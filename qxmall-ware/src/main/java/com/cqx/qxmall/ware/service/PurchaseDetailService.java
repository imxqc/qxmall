package com.cqx.qxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.ware.entity.PurchaseDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 22:14:43
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<PurchaseDetailEntity> listDetailByPurchaseId(Long id);
}

