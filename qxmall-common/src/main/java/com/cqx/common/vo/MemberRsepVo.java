package com.cqx.common.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/5 21:47
 */
@Data
@ToString
public class MemberRsepVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * ��Ա�ȼ�id
     */
    private Long levelId;
    /**
     * �û���
     */
    private String username;
    /**
     * ����
     */
    private String password;
    /**
     * �ǳ�
     */
    private String nickname;
    /**
     * �ֻ�����
     */
    private String mobile;
    /**
     * ����
     */
    private String email;
    /**
     * ͷ��
     */
    private String header;
    /**
     * �Ա�
     */
    private Integer gender;
    /**
     * ����
     */
    private Date birth;
    /**
     * ���ڳ���
     */
    private String city;
    /**
     * ְҵ
     */
    private String job;
    /**
     * ����ǩ��
     */
    private String sign;
    /**
     * �û���Դ
     */
    private Integer sourceType;
    /**
     * ����
     */
    private Integer integration;
    /**
     * �ɳ�ֵ
     */
    private Integer growth;
    /**
     * ����״̬
     */
    private Integer status;
    /**
     * ע��ʱ��
     */
    private Date createTime;

    //访问平台uid
    private String socialUid;

    //访问令牌
    private String accessToken;

    //令牌时间
    private Long expiresIn;
}