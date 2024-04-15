package com.cqx.qxmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.coupon.entity.CouponSpuCategoryRelationEntity;

import java.util.Map;

/**
 * �Ż�ȯ�������
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:30:10
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

