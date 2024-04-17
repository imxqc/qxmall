package com.cqx.qxmall.thirdparty;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class QxmallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        InputStream InputStream = new FileInputStream("\"E:\\桌面\\cqx\\pic\\006rChdqly1hagdz5bck5j30u010zq8l.jpg\"");

        ossClient.putObject("qxmall-xqc","gigi.jpg",InputStream);

        ossClient.shutdown();

        System.out.println("上传成功");
    }

}
