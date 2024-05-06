package com.cqx.qxmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.vo.SocialUser;
import com.cqx.qxmall.member.entity.MemberEntity;
import com.cqx.qxmall.member.exception.PhoneExistException;
import com.cqx.qxmall.member.exception.UserNameExistException;
import com.cqx.qxmall.member.vo.MemberLoginVo;
import com.cqx.qxmall.member.vo.UserRegisterVo;

import java.util.Map;

/**
 * ��Ա
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:42:54
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(UserRegisterVo userRegisterVo) throws PhoneExistException, UserNameExistException;

    void checkPhone(String phone) throws PhoneExistException;

    void checkUserName(String username) throws UserNameExistException;

    MemberEntity login(MemberLoginVo vo);

    MemberEntity login(SocialUser socialUser);
}

