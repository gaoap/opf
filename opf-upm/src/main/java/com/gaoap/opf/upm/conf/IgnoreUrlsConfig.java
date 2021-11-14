package com.gaoap.opf.upm.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径,暂时是静态配置，后面可以考虑改为加载数据库等方式
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "opf.upm.security.ignored")
@Component
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
