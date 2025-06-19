package com.sewon.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CacheConfig {

    private final CaffeineCacheProperties properties;

    @Bean
    public Cache<String, String> accessTokenBlackListCache() {
        return Caffeine.newBuilder()
            .expireAfterWrite(properties.getExpirationMinute(), TimeUnit.MINUTES)
            .maximumSize(properties.getMaximumSize())
            .build();
    }

    @Bean
    public Cache<String, String> refreshTokenBlackListCache() {
        return Caffeine.newBuilder()
            .expireAfterWrite(properties.getExpirationDay(), TimeUnit.DAYS)
            .maximumSize(properties.getMaximumSize())
            .build();
    }
}
