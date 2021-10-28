package com.gaoap.opf.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaoyd
 * @version 1.0.0
 * @ClassName NacosConfigController.java
 * @Description TODO
 * @createTime 2021年10月19日 22:10:00
 */
@Controller
@RestController
@RequestMapping("/nacos/test")
@RefreshScope
@Slf4j
public class NacosConfigController {
    @EventListener(RefreshScopeRefreshedEvent.class)
    void onRefresh(RefreshScopeRefreshedEvent event) {
        log.info("event.getName()" + event.getName());
        log.info("event.getSource()" + event.getSource());
        log.info("event.toString()" + event.toString());
    }


    //使用@value注解取值，它能取到值，但没有自动更新的功能
    // @Value(value = "${local.urlhead}" )
    // private String urlhead="123";

    //使用@nacosValue注解获取值，并开启自动更新
    @Value(value = "${urlhead:www.baidu.com}")
    private String urlheadAutoRefresh;

    @RequestMapping("/getValue")
    public String getValue() {
        log.info("urlheadAutoRefresh:" + urlheadAutoRefresh);
        return "urlhead:" + "1213" + "#####urlheadAutoRefresh:" + urlheadAutoRefresh;
    }
}
