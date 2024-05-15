package com.cqx.qxmall.seckill.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.fastjson.JSON;
import com.cqx.common.exception.BizCodeEnum;
import com.cqx.common.utils.R;
import org.springframework.context.annotation.Configuration;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/15 20:57
 * 定义限流后的提示信息,代替默认的blocked by sentinel...
 */
@Configuration
public class SecKillSentinelConfig {
    public SecKillSentinelConfig(){
        WebCallbackManager.setUrlBlockHandler((request, response, exception) -> {
            R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(error));
        });
    }
}
