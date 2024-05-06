package com.cqx.qxmall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cqx.common.constant.AuthServerConstant;
import com.cqx.common.utils.HttpUtils;
import com.cqx.common.utils.R;
import com.cqx.common.vo.SocialUser;
import com.cqx.qxmall.auth.feign.MemberFeignService;
import com.cqx.common.vo.MemberRsepVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/5 20:33
 */
@Controller
@Slf4j
public class Oauth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 根据code值获取access_token
     *
     * @param code
     * @return
     */
    @GetMapping("/oauth2.0/gitee/success")
    public String gitee(@RequestParam("code") String code, HttpSession session) throws Exception {

        Map<String, String> header = new HashMap<>();
        Map<String, String> query = new HashMap<>();

        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("client_id", "d7629e7d01175b4096fc22db8f7b586aa8a0287f3f2b381599482890d8bd9cb5");
        map.put("code", code);
        map.put("client_secret", "b76d27a6d387c6d27d03e99d56a0fc64a91f8e361877369ce02bd0512cbcc04e");
        map.put("redirect_uri", "http://auth.qxmall.com/oauth2.0/gitee/success");

        //获取access_token, expires_in
        HttpResponse response = HttpUtils.doPost("https://gitee.com", "/oauth/token", "post", header, query, map);

        //处理信息
        if (response.getStatusLine().getStatusCode() == 200) {
            //获取SocialUser信息
            String s = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(s, SocialUser.class);
            //从https://gitee.com/api/v5/user接口中获取id信息
            String uid = getUid(socialUser.getAccess_token());
            socialUser.setUid(uid);

            //第一次访问即注册 其他则直接登录
            R login = memberFeignService.login(socialUser);
            if (login.getCode() == 0) {
                MemberRsepVo loginData = login.getData("data", new TypeReference<MemberRsepVo>() {
                });
                log.info("用户登录成功,信息:{}", loginData);

                //将信息存入session
                session.setAttribute(AuthServerConstant.LOGIN_USER, loginData);

                //登录成功 跳转首页
                return "redirect:http://qxmall.com";
            } else {
                //重定向到登录页
                return "redirect:http://auth.qxmall.com/login.html";
            }
        } else {
            //重定向到登录页
            return "redirect:http://auth.qxmall.com/login.html";
        }

    }

    public String getUid(String token) throws Exception {
        Map<String, String> header = new HashMap<>();

        Map<String, String> map = new HashMap<>();
        map.put("access_token", token);

        HttpResponse response = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "get", header, map);

        if (response.getStatusLine().getStatusCode() == 200) {
            String s = EntityUtils.toString(response.getEntity());
            JSONObject object = JSON.parseObject(s);
            String id = object.getString("id");
            System.out.println("获取成功,uid = " + id);
            return id;
        } else {
            System.out.println("获取uid失败");
            return null;
        }
    }
}

