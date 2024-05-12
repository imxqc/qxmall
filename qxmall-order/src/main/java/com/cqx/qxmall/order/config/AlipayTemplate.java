package com.cqx.qxmall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cqx.qxmall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "9021000136694032";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC14gZYH0y4tObDnvPXa6abvvtwWRzje7OP65nU+ovYImF3MCMpy0hvgLK7Rns1VRkgRVOvRKdhh8EnJePwaRZLub/dCEtbUWE0zCc6ChB2IPFe8/CB6dFsMIl9vs/8boCkg8BdeDSsTe9m+VDYYb8bcWvhjgRadg37PKXt/zlAEj5x03SG47lzdiQYch1DukduA+cKVgge2dtph7car92LF8cwMIAuH112IzJ1m61YUIFMMAoZwbjnP3cThqEX9koMGv77btjVMv8bHhCNl7eVw3hg4gcnBnnUHdqyzrAZD2nEb3zigv2fiyIqotKJ6w8YPHcvpbyEePPaj5LHc9DZAgMBAAECggEAIRvl/8Ci3LTBlTaoOLy19YAMkWUHZzmrdDV1KVn6fvuzNlki7Ya9xt5sqr6nywuUqVU1aKBcv9pQhOnW1nqqvlov3PKXlSX5Rf0r3traKEbS6XAKhfukTceAH83OWqta65e4OsuY+wys2dfkqZ8RhNNeyg5qKHV5EhT2N57QMyNPyKVnwO9LcIpGRhvlt9NTHwNOBfuTP3zdD42g6IUQpu2UyLRhoViyrbJaGC3ivPD78fXQZyD64skGN0tGYZq/f+5QyQ2jWTXZ1lf/Nf5z7II0IXEWe2rg1WNxzu0jtADwsQx82KPeAHicLVFlYPslvkHy9jSRv/kkb5ybi8LnAQKBgQD234j/fZw101chPNqRSn6uCWdI9QFaXDXJsHhcup1TPMp/AXAfBLoS/lyNmAztmy83nugRdMqrr8gcjyrTV8yhRVIO6gLlDUvDLQz0I7EvLligWlrOntEe6+rvzwwlxdbplkG5cspYztybnP0f2EVZkJJSoaUXKJQgeEvaQR/Q8QKBgQC8m2gasEo1vBD1t4m7MC/ny4+Bbhd+30OfeAahh0yU8w4oPwjx7TgOSi0Maa6d3AlHNdkm2IWJKoHYxaV0zsXweeysGd4EXx35RpC2fGesASS0J4xUX9ZU2Znkh669+P7OKSzSTe4lxgZy2x8jYswlr1OYjlsdr8cIU8dzgZj+aQKBgB4/mWukmmVF9eEOscEnnYn5gPxBeN73eWezBaxm+SvfwPf61yAV1i7Jl9GJldvvLcPk5o5s/wA2U5CcQiUPFvWTptBI//3MuUG63WF0lhsv1JAhQgsbDoh5Y5nqhpSCPyjzzi3I0N48ym8Otz0VpLmqqyMQx0elIzEvTCv72sghAoGBAJ8CFh6KCcaLq3C9L+0Ujw7I20+vVTetWwGS22RLARGCaKkdm5j6+YtyldAlOQX1gAz9JPpPCSW/JIFxADEeMlqvMA1YMYlV95s1FsQC1hR5UkxgGzr3IWefnNLCYVvgFFIS/KrA8Mn/zWf/vJzEqoAK9FtliQPaMd6fY0PKOwYhAoGBALJV2lo2Hw+DVzqTVn9ROQCd2cXmfQPh4g2ZCwIjfEQd7dK4DHAIrOFBYctnA4x/Q7K1/1NgpqrVSfOs6ORB3iBHuY7EUwGt5/lh8hhczTN8QmlPfqTX3AEG2pHzpk0Qy9kaI8vxonk/cKDD7I9KfbzirV5dwXKymYOBhvG7oDFW";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxg59UDTF+A3cw70M63hP3CrQdN4HgkExYg3LNGbcoU8wkMw6ylrkxTEjbvHn/lxNi6e51ilFbYBJCUP5cONCGG7GAVkwONz8gTSF2Wghj2Z19Ca7YK2M1CtC9ZXAktCRaX5SgXPZe+ikYtDsNzRp1y9PD9seBt9Gbbr+lcEWC0euNvvzM/0fP6gyWU/k39Ud/BWIxsjxoqFc5TvzUFX6f3QynbGu4gArV5IvTrhTRAKFvMMPICcBkCL8ZFNUL5efCGvdG2iNd3FrgJyBdIf3xKz2pH7ymJX/LFUGUxV/he3tg4l5RFhWLPLmedmX2FQohPTC4aKgyQjWzDVNmQV2cQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    //todo 将域名改为内网穿透的域名
    private  String notify_url = "http://localhost:8080/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.qxmall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    private String timeout = "30m";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
