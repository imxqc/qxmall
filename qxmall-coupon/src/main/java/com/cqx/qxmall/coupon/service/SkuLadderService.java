package com.cqx.qxmall.coupon.service;

import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.coupon.entity.SkuLadderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author cqx
 * @email cqx@gmail.com
 * @date 2019-10-08 09:36:40
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

