package com.gaoap.opf.upm;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//开启jasypt加密解密服务。需要解密的配置，添加标识ENC(密文)
@EnableEncryptableProperties
public class OpfUpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpfUpmApplication.class, args);
    }

}
