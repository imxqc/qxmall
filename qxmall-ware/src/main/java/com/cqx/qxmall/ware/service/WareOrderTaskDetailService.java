package com.cqx.qxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 22:14:43
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

