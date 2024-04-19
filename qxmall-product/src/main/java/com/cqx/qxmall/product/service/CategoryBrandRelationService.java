package com.cqx.qxmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.product.entity.BrandEntity;
import com.cqx.qxmall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * Ʒ�Ʒ������
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 20:22:28
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存详细信息 brandName catelogName
     *
     * @param categoryBrandRelation
     */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);

    List<BrandEntity> getBrandsByCatId(Long catId);
}

