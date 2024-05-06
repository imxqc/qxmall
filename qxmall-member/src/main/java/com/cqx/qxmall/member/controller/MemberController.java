package com.cqx.qxmall.member.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.cqx.common.constant.AuthServerConstant;
import com.cqx.common.exception.BizCodeEnum;
import com.cqx.common.vo.SocialUser;
import com.cqx.qxmall.member.exception.PhoneExistException;
import com.cqx.qxmall.member.exception.UserNameExistException;
import com.cqx.qxmall.member.feign.CouponFeignService;
import com.cqx.qxmall.member.vo.MemberLoginVo;
import com.cqx.qxmall.member.vo.UserRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cqx.qxmall.member.entity.MemberEntity;
import com.cqx.qxmall.member.service.MemberService;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.R;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * ��Ա
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:42:54
 */
@RestController
@Slf4j
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponFeignService couponFeignService;

    /**
     * 社交登录功能
     * 查询是否注册过 未注册则注册
     * 注册了则更新令牌,令牌时间
     *
     * @param socialUser
     * @return
     */
    @PostMapping("/oauth2/login")
    public R login(@RequestBody SocialUser socialUser) {

        MemberEntity memberEntity = memberService.login(socialUser);
        if (memberEntity != null) {
            // 登录成功
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.SOCIALUSER_LOGIN_ERROR.getCode(), BizCodeEnum.SOCIALUSER_LOGIN_ERROR.getMsg());
        }
    }


    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo) {

        MemberEntity memberEntity = memberService.login(vo);
        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGINACTT_PASSWORD_ERROR.getCode(), BizCodeEnum.LOGINACTT_PASSWORD_ERROR.getMsg());
        }
    }

    @PostMapping("/regist")
    public R register(@RequestBody UserRegisterVo userRegisterVo) {

        try {
            memberService.register(userRegisterVo);
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UserNameExistException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @RequestMapping("/coupons")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("zhangsan");

        R membercoupons = couponFeignService.membercoupons();

        return R.ok().put("member", memberEntity).put("coupons", membercoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
