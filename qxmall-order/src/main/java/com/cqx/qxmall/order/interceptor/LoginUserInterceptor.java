package com.cqx.qxmall.order.interceptor;

import com.cqx.common.constant.AuthServerConstant;
import com.cqx.common.vo.MemberRsepVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/7 22:00
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    public static ThreadLocal<MemberRsepVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        // 这个支付跳转登录页的请求直接放行
        AntPathMatcher matcher = new AntPathMatcher();

        boolean match1 =   matcher .match("/order/order/status/**", uri);
        boolean match2 =   matcher .match("/payed/notify", uri);
        if(match1 || match2){
            return true;
        }

        HttpSession session = request.getSession();
        MemberRsepVo memberRsepVo = (MemberRsepVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(memberRsepVo != null){
            loginUser.set(memberRsepVo);
            return true;
        }else{
            // 没登陆就去登录
            session.setAttribute("msg", AuthServerConstant.NOT_LOGIN);
            response.sendRedirect("http://auth.qxmall.com/login.html");
            return false;
        }
    }

}
