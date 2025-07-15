package com.sewon.redis.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sewon.notification.dto.NotificationResult;
import com.sewon.notification.repository.NotificationCacheRepository;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CacheNotificationRepository implements NotificationCacheRepository {

    private final RedisTemplate<String, String> strRedisTemplate;
    private final ObjectMapper mapper;


    @Override
    public void save(String key, List<NotificationResult> values, Duration ttl) {
        try {
            strRedisTemplate.opsForValue().set(key, mapper.writeValueAsString(values), ttl);
        } catch (IOException e) {
            log.error("io ex message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NotificationResult> get(String key) {
        try {
            String data = strRedisTemplate.opsForValue().get(key);
            if (data != null) {
                return mapper.readValue(data, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            log.error("io ex message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    @Override
    public void delete(String key) {
        strRedisTemplate.delete(key);
    }
}
