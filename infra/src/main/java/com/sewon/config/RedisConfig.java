package com.sewon.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory lettuceRedisConnectionFactory(RedisProperties properties) {
        return new LettuceConnectionFactory(
            new RedisStandaloneConfiguration(properties.getHost(), properties.getPort())
        );
    }

    @Bean
    public RedisTemplate<String, String> strRedisTemplate(
        RedisConnectionFactory lettuceRedisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
