package com.cqx.qxmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.member.entity.MemberReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * ��Ա�ջ���ַ
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:42:54
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MemberReceiveAddressEntity> getAddress(Long memberId);
}

