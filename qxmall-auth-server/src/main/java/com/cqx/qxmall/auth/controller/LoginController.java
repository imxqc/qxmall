package com.cqx.qxmall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.cqx.common.constant.AuthServerConstant;
import com.cqx.common.exception.BizCodeEnum;
import com.cqx.common.utils.R;
import com.cqx.common.vo.MemberRsepVo;
import com.cqx.qxmall.auth.feign.MemberFeignService;
import com.cqx.qxmall.auth.feign.ThirdPartFeignService;
import com.cqx.qxmall.auth.vo.UserLoginVo;
import com.cqx.qxmall.auth.vo.UserRegisterVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/4 22:58
 */
@Data
@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Slf4j
@Controller
public class LoginController {


    private String effectiveTime;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 登录了则跳转首页
     * @param session
     * @return
     */
    @GetMapping({"/login.html"})
    public String loginPage(HttpSession session){
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(attribute == null){
            return "login";
        }
        return "redirect:http://qxmall.com";
    }


    @PostMapping("/login")
    public String login(UserLoginVo userLoginVo, RedirectAttributes redirectAttributes, HttpSession session) {
        R r = memberFeignService.login(userLoginVo);
        if (r.getCode() == 0) {
            // 登录成功
            MemberRsepVo loginData = r.getData("data", new TypeReference<MemberRsepVo>() {
            });

            //将信息存入session
            session.setAttribute(AuthServerConstant.LOGIN_USER, loginData);

            return "redirect:http://qxmall.com";
        } else {
            HashMap<String, String> error = new HashMap<>();
            // 获取错误信息
            error.put("msg", r.getData("msg", new TypeReference<String>() {
            }));
            redirectAttributes.addFlashAttribute("errors", error);
            return "redirect:http://auth.qxmall.com/login.html";
        }
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {

        // TODO 接口防刷
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);

        if (!StringUtils.isEmpty(redisCode)) {
            long CuuTime = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - CuuTime < 60 * 1000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        Random random = new Random();
        int cod = 100000 + random.nextInt(900000);
        String code = String.valueOf(cod);
        //存入验证码和存入的时间
        String redis_code = code + "_" + System.currentTimeMillis();

        long expTime = Long.parseLong(effectiveTime);

        // 缓存验证码
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, redis_code, expTime, TimeUnit.MINUTES);

        try {
            return thirdPartFeignService.sendCode(phone, code);
        } catch (Exception e) {
            log.warn("远程调用不知名错误 [无需解决]");
        }

        return R.ok();
    }


    /**
     * 注册功能
     * RedirectAttributes 重定向后携带数据
     *
     * @param vo
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/regist")
    public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes) {

        //后端校验数据出错,将信息放入model,并且重定向到注册页
        if (result.hasErrors()) {
            //将错误信息存入map当中
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, fieldError -> fieldError.getDefaultMessage()));

            // addFlashAttribute 这个数据只取一次
            redirectAttributes.addFlashAttribute("errors", errors);

            //校验出错,重定向到注册页 (session获取数据)
            return "redirect:http://auth.qxmall.com/reg.html";
        }

        //数据校验成功 远程接口进行注册
        //校验验证码 是否正确
        String code = vo.getCode();
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());

        if (!StringUtils.isEmpty(redisCode)) {
            if (code.equals(redisCode.split("_")[0])) {
                //删除验证码 令牌机制
                //todo 暂时停止删除验证码逻辑 节省资源
//                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                //远程注册
                R r = memberFeignService.register(vo);

                if (r.getCode() == 0) {
                    return "redirect:http://auth.qxmall.com/login.html";
                } else {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData("msg", new TypeReference<String>() {
                    }));

                    redirectAttributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.qxmall.com/reg.html";
                }

            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "验证码错误");
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.qxmall.com/reg.html";
            }

        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");

            redirectAttributes.addFlashAttribute("errors", errors);
            //校验出错,重定向到注册页 (session获取数据)
            return "redirect:http://auth.qxmall.com/reg.html";
        }

    }

}
