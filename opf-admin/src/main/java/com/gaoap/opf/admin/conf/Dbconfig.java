package com.gaoap.opf.admin.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaoyd
 * @version 1.0.0
 * @ClassName Dbconfig.java
 * @Description TODO
 * @createTime 2021年10月22日 20:59:00
 */
@Configuration
@ConfigurationProperties(prefix = "remote", ignoreUnknownFields = false)
public class Dbconfig {
}
