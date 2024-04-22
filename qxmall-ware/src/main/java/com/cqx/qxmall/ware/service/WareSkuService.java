package com.cqx.qxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.to.es.SkuHasStockVo;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 22:14:43
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);


    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);
}

