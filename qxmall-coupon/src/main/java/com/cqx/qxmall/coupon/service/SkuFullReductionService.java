package com.cqx.qxmall.coupon.service;

import com.cqx.common.to.SkuReductionTo;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.coupon.entity.SkuFullReductionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author cqx
 * @email cqx@gmail.com
 * @date 2019-10-08 09:36:40
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo reductionTo);


}

