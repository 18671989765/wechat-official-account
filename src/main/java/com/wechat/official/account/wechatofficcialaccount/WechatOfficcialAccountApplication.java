package com.wechat.official.account.wechatofficcialaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan(basePackages={"com.wechat.official.account.wechatofficcialaccount.*"})
public class WechatOfficcialAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatOfficcialAccountApplication.class, args);
    }


}
