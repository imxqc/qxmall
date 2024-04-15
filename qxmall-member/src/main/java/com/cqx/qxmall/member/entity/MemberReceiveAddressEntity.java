package com.cqx.qxmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ��Ա�ջ���ַ
 * 
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:42:54
 */
@Data
@TableName("ums_member_receive_address")
public class MemberReceiveAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member_id
	 */
	private Long memberId;
	/**
	 * �ջ�������
	 */
	private String name;
	/**
	 * �绰
	 */
	private String phone;
	/**
	 * ��������
	 */
	private String postCode;
	/**
	 * ʡ��/ֱϽ��
	 */
	private String province;
	/**
	 * ����
	 */
	private String city;
	/**
	 * ��
	 */
	private String region;
	/**
	 * ��ϸ��ַ(�ֵ�)
	 */
	private String detailAddress;
	/**
	 * ʡ��������
	 */
	private String areacode;
	/**
	 * �Ƿ�Ĭ��
	 */
	private Integer defaultStatus;

}
