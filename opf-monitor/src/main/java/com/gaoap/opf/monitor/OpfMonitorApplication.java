package com.gaoap.opf.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer // 开启 springboot admin 服务端
public class OpfMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpfMonitorApplication.class, args);
    }

}
