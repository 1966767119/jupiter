package com.vsniu.jupiter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: wangfeng7
 * @Date: 2020/3/10 16:19
 * @Description: zk的初始化参数
 */
@Data
@Component
@ConfigurationProperties(prefix = "curator")
public class WrapperZK {

    private int retryCount;

    private int elapsedTimeMs;

    private String connectString;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;
}
