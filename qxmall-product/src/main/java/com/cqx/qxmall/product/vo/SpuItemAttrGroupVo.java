package com.cqx.qxmall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/29 21:19
 */
@Data
@ToString
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}
