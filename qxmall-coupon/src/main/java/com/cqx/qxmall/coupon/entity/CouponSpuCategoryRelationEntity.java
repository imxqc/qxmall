package com.cqx.qxmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * �Ż�ȯ�������
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:30:10
 */
@Data
@TableName("sms_coupon_spu_category_relation")
public class CouponSpuCategoryRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * �Ż�ȯid
     */
    private Long couponId;
    /**
     * ��Ʒ����id
     */
    private Long categoryId;
    /**
     * ��Ʒ��������
     */
    private String categoryName;

}
