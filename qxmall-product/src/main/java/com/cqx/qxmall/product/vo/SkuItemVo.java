package com.cqx.qxmall.product.vo;

import com.cqx.qxmall.product.entity.SkuImagesEntity;
import com.cqx.qxmall.product.entity.SkuInfoEntity;
import com.cqx.qxmall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: SkuItemVo</p>
 * Description：
 * date：2020/6/24 13:33
 */
@Data
public class SkuItemVo {
    //sku基本信息
    SkuInfoEntity info;

    //sku图片信息
    List<SkuImagesEntity> images;

    //spu销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;

    //spu介绍
    SpuInfoDescEntity desp;

    //spu规则参数信息
    List<SpuItemAttrGroupVo> groupAttrs;

    //库存信息
    Boolean hasStock = true;
}
