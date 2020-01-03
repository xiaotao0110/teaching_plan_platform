package com.eureka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 用于监听eureka服务停机通知  可以扩展：发短信，邮箱，微信通知等
 */
@Slf4j
@Component
public class EurekaStateChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        log.info("服务 [" + appName + ":" + serverId + "] 挂啦......");
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        log.debug("服务[" + event.getInstanceInfo().getAppName() + " ]注册成功啦......");
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        log.debug("心跳检测服务 [" + event.getAppName() + ":" + event.getServerId() + "]......");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        log.debug("Eureka注册中心启动......");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        log.debug("Eureka Server启动......");
    }


}
