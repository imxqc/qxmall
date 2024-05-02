package com.cqx.qxmall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/29 21:18
 */
@Data
@ToString
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
