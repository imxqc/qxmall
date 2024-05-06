package com.cqx.qxmall.auth.feign;

import com.cqx.common.utils.R;
import com.cqx.common.vo.SocialUser;
import com.cqx.qxmall.auth.vo.UserLoginVo;
import com.cqx.qxmall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/5 16:53
 */
@FeignClient("qxmall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/regist")
    R register(@RequestBody UserRegisterVo userRegisterVo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping("/member/member/oauth2/login")
    R login(@RequestBody SocialUser socialUser);
}