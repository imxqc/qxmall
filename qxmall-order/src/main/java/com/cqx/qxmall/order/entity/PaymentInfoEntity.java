package com.cqx.qxmall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ֧����Ϣ��
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:51:42
 */
@Data
@TableName("oms_payment_info")
public class PaymentInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * �����ţ�����ҵ��ţ�
     */
    private String orderSn;
    /**
     * ����id
     */
    private Long orderId;
    /**
     * ֧����������ˮ��
     */
    private String alipayTradeNo;
    /**
     * ֧���ܽ��
     */
    private BigDecimal totalAmount;
    /**
     * ��������
     */
    private String subject;
    /**
     * ֧��״̬
     */
    private String paymentStatus;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ȷ��ʱ��
     */
    private Date confirmTime;
    /**
     * �ص�����
     */
    private String callbackContent;
    /**
     * �ص�ʱ��
     */
    private Date callbackTime;

}
