package com.cqx.qxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.ware.entity.WareInfoEntity;
import com.cqx.qxmall.ware.vo.FareVo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 22:14:43
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);


    FareVo getFare(Long addrId);
}

