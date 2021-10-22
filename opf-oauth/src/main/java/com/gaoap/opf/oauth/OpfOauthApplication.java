package com.gaoap.opf.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//resilience4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OpfOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpfOauthApplication.class, args);
    }

}
