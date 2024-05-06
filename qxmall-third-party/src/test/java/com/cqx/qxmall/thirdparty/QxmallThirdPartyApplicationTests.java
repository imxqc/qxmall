package com.cqx.qxmall.thirdparty;


import com.cqx.qxmall.thirdparty.component.SmsComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QxmallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Resource
    SmsComponent smsComponent;

    @Test
    public void test(){
        System.out.println(smsComponent);
        String s = smsComponent.sendSmsCode("13425178333", "279315");
        System.out.println("s = " );
    }

    @Test
    public void test2(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        System.out.println("code = " + code);
    }

    @Test
    public void contextLoads() throws FileNotFoundException {
        InputStream InputStream = new FileInputStream("\"E:\\桌面\\cqx\\pic\\006rChdqly1hagdz5bck5j30u010zq8l.jpg\"");

        ossClient.putObject("qxmall-xqc", "gigi.jpg", InputStream);

        ossClient.shutdown();

        System.out.println("上传成功");
    }

}
