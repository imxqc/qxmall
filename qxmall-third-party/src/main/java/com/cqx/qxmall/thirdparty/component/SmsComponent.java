package com.cqx.qxmall.thirdparty.component;

import com.cqx.common.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/3 22:12
 */
@Data
@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Component
public class SmsComponent {
    private String host;

    private String path;

    private String appCode;

    private String effectiveTime;

    private String smsSignId;

    private String templateId;


    public String sendSmsCode(String phone, String code) {
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        String param = "**code**:" + code + ",**minute**:" + effectiveTime;
//        querys.put("param", "**code**:12345,**minute**:5");
        querys.put("param", param);


//smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", smsSignId);
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<String, String>();

        HttpResponse response = null;

        try {
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            //getStatusLine 获取响应状态行
            //getStatusCode 获取响应状态码
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "fail_" + response.getStatusLine().getStatusCode();
    }

}
