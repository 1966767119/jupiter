package com.vsniu.jupiter.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Auther: wangfeng7
 * @Date: 2020/3/10 16:25
 * @Description: curator 配置
 */
@Configuration
public class ZkConfiguration {

    @Resource
    private WrapperZK wrapperZK;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(){
        return CuratorFrameworkFactory.newClient(
                wrapperZK.getConnectString(),
                wrapperZK.getSessionTimeoutMs(),
                wrapperZK.getConnectionTimeoutMs(),
                new RetryNTimes(wrapperZK.getRetryCount(),wrapperZK.getElapsedTimeMs()));
    }
}
