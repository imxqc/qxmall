package com.cqx.qxmall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.cqx.common.enume.OrderStatusEnum;
import com.cqx.common.exception.NotStockException;
import com.cqx.common.to.es.SkuHasStockVo;
import com.cqx.common.to.mq.OrderTo;
import com.cqx.common.to.mq.StockDetailTo;
import com.cqx.common.to.mq.StockLockedTo;
import com.cqx.common.utils.R;
import com.cqx.qxmall.ware.entity.WareOrderTaskDetailEntity;
import com.cqx.qxmall.ware.entity.WareOrderTaskEntity;
import com.cqx.qxmall.ware.feign.OrderFeignService;
import com.cqx.qxmall.ware.feign.ProductFeignService;
import com.cqx.qxmall.ware.service.WareOrderTaskDetailService;
import com.cqx.qxmall.ware.service.WareOrderTaskService;
import com.cqx.qxmall.ware.vo.OrderItemVo;
import com.cqx.qxmall.ware.vo.OrderVo;
import com.cqx.qxmall.ware.vo.WareSkuLockVo;
import com.rabbitmq.client.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;

import com.cqx.qxmall.ware.dao.WareSkuDao;
import com.cqx.qxmall.ware.entity.WareSkuEntity;
import com.cqx.qxmall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    private WareOrderTaskService orderTaskService;

    @Autowired
    private WareOrderTaskDetailService orderTaskDetailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderFeignService orderFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         * skuId: 1
         * wareId: 2
         */
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }


        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //1、判断如果还没有这个库存记录新增
        List<WareSkuEntity> entities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (entities == null || entities.size() == 0) {
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setStock(skuNum);
            skuEntity.setWareId(wareId);
            skuEntity.setStockLocked(0);
            //TODO 远程查询sku的名字，如果失败，整个事务无需回滚
            //1、自己catch异常
            //TODO 还可以用什么办法让异常出现以后不回滚？高级
            try {
                R info = productFeignService.info(skuId);
                Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");

                if (info.getCode() == 0) {
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {

            }

            wareSkuDao.insert(skuEntity);
        } else {
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }

    }

    @Override
    public List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(id -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();

            skuHasStockVo.setSkuId(id);

            Long count = baseMapper.getSkuStock(id);
            skuHasStockVo.setHasStock(count == null ? false : count > 0);

            return skuHasStockVo;
        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * 为某个订单锁定库存
     *     @Transactional(rollbackFor = NotStockException.class)  当抛出该方法时,数据库回滚
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = NotStockException.class)
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) {

        // 当定库存之前先保存库存工作单详情 可以通过该详情表查看商品库存锁定情况  以便后来消息撤回
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(vo.getOrderSn());
        orderTaskService.save(taskEntity);

        List<OrderItemVo> locks = vo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock hasStock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            hasStock.setSkuId(skuId);
            hasStock.setNum(item.getCount());

            // 查询这两个商品在哪有库存
            List<Long> wareIds = wareSkuDao.listWareIdHasSkuStock(skuId);
            hasStock.setWareId(wareIds);
            return hasStock;
        }).collect(Collectors.toList());


        for (SkuWareHasStock hasStock : collect) {
            Boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();

            if(wareIds == null || wareIds.size() == 0){
                // 没有任何仓库有这个库存
                throw new NotStockException(skuId.toString());
            }

            for (Long wareId : wareIds) {
                // 成功就返回 1 失败返回0
                Long count = wareSkuDao.lockSkuStock(skuId, wareId, hasStock.getNum());
                if(count == 1){
                    // TODO 告诉MQ库存锁定成功 一个订单锁定成功 消息队列就会有一个消息
                    WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity(null,skuId,"",hasStock.getNum() ,taskEntity.getId(),wareId,1);
                    orderTaskDetailService.save(detailEntity);
                    StockLockedTo stockLockedTo = new StockLockedTo();
                    stockLockedTo.setId(taskEntity.getId());
                    StockDetailTo detailTo = new StockDetailTo();
                    BeanUtils.copyProperties(detailEntity, detailTo);
                    // 防止回滚以后找不到数据 把详细信息页
                    stockLockedTo.setDetailTo(detailTo);

                    rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked" ,stockLockedTo);
                    skuStocked = true;
                    break;
                }
                // 当前仓库锁定失败 重试下一个仓库
            }
            if(!skuStocked){
                // 当前商品在所有仓库都没锁柱
                throw new NotStockException(skuId.toString());
            }
        }
        // 3.全部锁定成功
        return true;
    }

    @Data
    class SkuWareHasStock{

        private Long skuId;

        private List<Long> wareId;

        private Integer num;
    }


    /**
     * 去数据库解锁对应的数据 并改变解锁状态值z
     * @param skuId
     * @param wareId
     * @param num
     * @param taskDeailId
     */
    private void unLock(Long skuId,Long wareId, Integer num, Long taskDeailId){
        // 更新库存
        wareSkuDao.unlockStock(skuId, wareId, num);
        // 更新库存工作单的状态
        WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity();
        detailEntity.setId(taskDeailId);
        detailEntity.setLockStatus(2);
        orderTaskDetailService.updateById(detailEntity);
    }

    /**
     * 解锁库存
     * 	查询数据库关系这个订单的详情
     * 		有: 证明库存锁定成功
     * 			1.没有这个订单, 必须解锁
     * 			2.有这个订单 不是解锁库存
     * 				订单状态：已取消,解锁库存
     * 				没取消：不能解锁	;
     * 		没有：就是库存锁定失败， 库存回滚了 这种情况无需回滚
     */
    @Override
    public void unlockStock(StockLockedTo to) {
        log.info("\n收到解锁库存的消息");
        // 库存id
        Long id = to.getId();
        StockDetailTo detailTo = to.getDetailTo();
        Long detailId = detailTo.getId();
        /**
         * 解锁库存
         * 	查询数据库关系这个订单的详情
         * 		有: 证明库存锁定成功
         * 			1.没有这个订单, 必须解锁
         * 			2.有这个订单 不是解锁库存
         * 				订单状态：已取消,解锁库存
         * 				没取消：不能解锁	;
         * 		没有：就是库存锁定失败， 库存回滚了 这种情况无需回滚
         */
        WareOrderTaskDetailEntity byId = orderTaskDetailService.getById(detailId);
        if(byId != null){
            // 解锁
            WareOrderTaskEntity taskEntity = orderTaskService.getById(id);
            String orderSn = taskEntity.getOrderSn();
            // 根据订单号 查询订单状态 已取消才解锁库存
            R orderStatus = orderFeignService.getOrderStatus(orderSn);
            if(orderStatus.getCode() == 0){
                // 订单数据返回成功
                OrderVo orderVo = orderStatus.getData(new TypeReference<OrderVo>() {});
                // 订单不存在
                if(orderVo == null || orderVo.getStatus() == OrderStatusEnum.CANCLED.getCode()){
                    // 订单已取消 状态1 已锁定  这样才可以解锁
                    if(byId.getLockStatus() == 1){
                        unLock(detailTo.getSkuId(), detailTo.getWareId(), detailTo.getSkuNum(), detailId);
                    }
                }
            }else{
                // 消息拒绝 重新放回队列 让别人继续消费解锁
                throw new RuntimeException("远程服务失败");
            }
        }else{
            // 无需解锁
        }
    }

    /**
     * 防止订单服务卡顿, 库存消息优先到期, 导致本来订单取消但库存消费
     * 当mq收到orderTo的消息后就说明订单正在或者已经取消了, 所以本方法将订单状态为锁定的库存解锁
     * @param to
     */
    @Transactional
    @Override
    public void unlockStock(OrderTo to) {
        log.info("\n订单超时自动关闭,准备解锁库存");
        String orderSn = to.getOrderSn();
        // 查一下最新的库存状态 防止重复解锁库存[Order服务可能会提前解锁]
        WareOrderTaskEntity taskEntity = orderTaskService.getOrderTaskByOrderSn(orderSn);

        Long taskEntityId = taskEntity.getId();
        // 按照工作单找到所有 没有解锁的库存 进行解锁 状态为1等于已锁定
        List<WareOrderTaskDetailEntity> entities = orderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                .eq("task_id", taskEntityId).eq("lock_status", 1));

        //进行解锁
        for (WareOrderTaskDetailEntity entity : entities) {
            unLock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum(), entity.getId());
        }
    }


}