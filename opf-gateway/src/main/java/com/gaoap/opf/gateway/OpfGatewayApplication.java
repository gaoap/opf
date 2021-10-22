package com.gaoap.opf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OpfGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpfGatewayApplication.class, args);
    }

}
