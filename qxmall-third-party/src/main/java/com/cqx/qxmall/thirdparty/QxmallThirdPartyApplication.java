package com.cqx.qxmall.thirdparty;

import com.cqx.qxmall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(SmsComponent.class)
public class QxmallThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallThirdPartyApplication.class, args);
    }


}
