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

    private final RedisTemplate<String, String> stringRedisTemplate;


    @Override
    public void save(String key, String value, Duration ttl) {
        stringRedisTemplate.opsForValue().set(key, value, ttl);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Duration getExpiration(String key) {
        return Duration.ofMinutes(stringRedisTemplate.getExpire(key, TimeUnit.MINUTES));
    }
}
