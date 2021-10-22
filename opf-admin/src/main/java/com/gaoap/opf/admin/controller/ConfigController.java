package com.gaoap.opf.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
@Slf4j
public class ConfigController {
    @EventListener(RefreshScopeRefreshedEvent.class)
    void onRefresh(RefreshScopeRefreshedEvent event) {
        log.info("event.getName()"+event.getName());
        log.info("event.getSource()"+event.getSource());
        log.info("event.toString()"+event.toString());
    }
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    /**
     * http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}