package com.gaoap.opf.admin;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * springcloud  的关联版本非常容易出问题。
 * 应用会出现各种异常。所以一定要使用毕业版本：
 * 参考：https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEncryptableProperties

public class OpfAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpfAdminApplication.class, args);
    }

}
