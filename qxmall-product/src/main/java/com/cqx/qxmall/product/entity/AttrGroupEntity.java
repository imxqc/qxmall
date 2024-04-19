package com.cqx.qxmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ���Է���
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 20:22:28
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ����id
     */
    @TableId
    private Long attrGroupId;
    /**
     * ����
     */
    private String attrGroupName;
    /**
     * ����
     */
    private Integer sort;
    /**
     * ����
     */
    private String descript;
    /**
     * ��ͼ��
     */
    private String icon;
    /**
     * ��������id
     */
    private Long catelogId;

    /**
     * 该节点的完整路径 [父,子,孙]
     */
    @TableField(exist = false)
    private Long[] catelogPath;

}
