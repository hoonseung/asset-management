package com.sewon.redis.token;

import com.sewon.security.repository.TokenRepository;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisJwtRepository implements TokenRepository {

    private final RedisTemplate<String, String> strRedisTemplate;


    @Override
    public void save(String key, String value, Duration ttl) {
        strRedisTemplate.opsForValue().set(key, value, ttl);
    }

    @Override
    public String get(String key) {
        return strRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Duration getExpiration(String key) {
        return Duration.ofMinutes(strRedisTemplate.getExpire(key, TimeUnit.MINUTES));
    }
}
