package com.cqx.qxmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.product.entity.AttrAttrgroupRelationEntity;
import com.cqx.qxmall.product.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * ����&���Է������
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 20:22:28
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatch(List<AttrGroupRelationVo> vos);
}

