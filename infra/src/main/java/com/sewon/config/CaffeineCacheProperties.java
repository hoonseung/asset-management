package com.sewon.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "caffeine")
public class CaffeineCacheProperties {

    private int expirationMinute;
    private int expirationDay;
    private long maximumSize;

}
