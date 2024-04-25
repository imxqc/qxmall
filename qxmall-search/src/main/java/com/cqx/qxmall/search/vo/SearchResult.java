package com.cqx.qxmall.search.vo;

import com.cqx.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SearchResponse</p>
 * Description：包含页面需要的所有信息
 */
@Data
public class SearchResult {

    /**
     * 查询到的所有商品信息
     */
    private List<SkuEsModel> products;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页码
     */
    private Integer totalPages;

    /**
     * 当前查询到的结果,所有涉及到的品牌
     */
    private List<BrandVo> brands;

    /**
     * 当前查询到的结果,所有涉及到的分类
     */
    private List<CatalogVo> catalogs;


    /**
     * 当前查询的结果 所有涉及到所有属性
     */
    private List<AttrVo> attrs;

//	================以上是返回给页面的所有信息================

    @Data
    public static class BrandVo {

        private Long brandId;

        private String brandName;

        private String brandImg;
    }

    @Data
    public static class CatalogVo {

        private Long catalogId;

        private String catalogName;
    }

    @Data
    public static class AttrVo {

        private Long attrId;

        private String attrName;

        private List<String> attrValue;
    }
}
