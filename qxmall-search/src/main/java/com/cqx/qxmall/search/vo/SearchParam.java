package com.cqx.qxmall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/25 21:12
 */
@Data
public class SearchParam {
    /**
     * 全文匹配关键字
     */
    private String keyword;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 排序
     * 格式 排序属性_ASC/DESC
     */
    private String sort;

    private Integer hasStock;

    /**
     * 价格区间
     */
    private String skuPrice;

    /**
     * 品牌id 可以多选
     */
    private List<Long> brandId;

    /**
     * 按照属性进行筛选 格式:  id_attrval1:attrval2...
     * id和属性用 _ 分隔
     * 如有多个属性 用:分隔
     */
    private List<String> attrs;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 原生所有查询属性
     */
    private String _queryString;
}
