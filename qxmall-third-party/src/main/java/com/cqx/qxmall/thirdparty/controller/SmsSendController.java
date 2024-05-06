package com.cqx.qxmall.thirdparty.controller;

import com.cqx.common.exception.BizCodeEnum;
import com.cqx.common.utils.R;
import com.cqx.qxmall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/4 22:50
 */
@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Autowired
    SmsComponent smsComponent;


    /**
     * 提供给别的服务进行调用的
     */
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code){
        String res = smsComponent.sendSmsCode(phone, code);
        System.out.println("res = " + res);

        return R.ok();
    }
}
