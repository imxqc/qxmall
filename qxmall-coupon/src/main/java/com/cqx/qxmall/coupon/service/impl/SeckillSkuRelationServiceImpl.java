package com.cqx.qxmall.coupon.service.impl;

import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;
import com.cqx.qxmall.coupon.dao.SeckillSkuRelationDao;
import com.cqx.qxmall.coupon.entity.SeckillSkuRelationEntity;
import com.cqx.qxmall.coupon.service.SeckillSkuRelationService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SeckillSkuRelationEntity> wrapper = new QueryWrapper<>();
        String promotionSessionId = (String) params.get("promotionSessionId");
        // 场次ID不是空
        if(!StringUtils.isEmpty(promotionSessionId)){
            wrapper.eq("promotion_session_id",promotionSessionId);
        }

        IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }
}