package com.cqx.qxmall.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqx.common.utils.HttpUtils;
import com.cqx.common.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QxmallAuthServerApplicationTests {

    @Test
    public void test(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches("111111", "$2a$10$YUg75FYjes1DTvh3go5a9ewnNnKU/7IzK5S358TWlRht/XOq1nTHK");
        System.out.println("matches = " + matches);

    }


    /**
     * 获取token
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Map<String,String> header = new HashMap<>();
        Map<String,String> query = new HashMap<>();

        Map<String,String> map = new HashMap<>();
        map.put("grant_type","authorization_code");
        map.put("client_id","d7629e7d01175b4096fc22db8f7b586aa8a0287f3f2b381599482890d8bd9cb5");
        map.put("code","2d60a5fd11d4b69bbfd10b91ba86d4a31aa13bcaa30b3c55b01b84f4dc7b2757");
        map.put("client_secret","b76d27a6d387c6d27d03e99d56a0fc64a91f8e361877369ce02bd0512cbcc04e");
        map.put("redirect_uri","http://auth.qxmall.com/oauth2.0/gitee/success");

        HttpResponse response = HttpUtils.doPost("https://gitee.com", "/oauth/token", "post", header, query, map);

        if (response.getStatusLine().getStatusCode()==200){
            String s = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(s, SocialUser.class);
            System.out.println("socialUser = " + socialUser);
        }else{
            String s = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(s, SocialUser.class);
            System.out.println("socialUser = " + socialUser);
            System.out.println("失败");
        }
    }


    /**
     * 获取uid
     */
    @Test
    public void test3() throws Exception {
        Map<String,String> header = new HashMap<>();

        Map<String,String> map = new HashMap<>();
        map.put("access_token","b36147db3fef2b68ac233db2bf3c65d6");

        HttpResponse response = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "get", header, map);

        if (response.getStatusLine().getStatusCode()==200){
            String s = EntityUtils.toString(response.getEntity());
            JSONObject object = JSON.parseObject(s);
            String id = object.getString("id");
            System.out.println("id = " + id);
        }else{
            System.out.println("fail");
        }
    }

}
