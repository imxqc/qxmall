package com.cqx.qxmall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/5 15:15
 */
@Data
public class UserRegisterVo {

    @Length(min = 6,max = 20,message = "用户名长度必须在6-20之间")
    @NotEmpty(message = "用户名必须提交")
    private String userName;

    @Length(min = 6,max = 20,message = "密码长度必须在6-20之间")
    @NotEmpty(message = "密码必须提交")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "验证码必须填写")
    private String code;
}