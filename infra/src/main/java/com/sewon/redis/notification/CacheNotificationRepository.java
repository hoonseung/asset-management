package com.sewon.redis.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sewon.notification.dto.NotificationResult;
import com.sewon.notification.repository.NotificationCacheRepository;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
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

    @Override
    public void put(String hash, Long key, Object value) {
        try {
            HashOperations<String, Object, Object> hashOperations = strRedisTemplate.opsForHash();
            hashOperations.put(hash, key, mapper.writeValueAsString(value));
        } catch (IOException e) {
            log.error("io ex message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Long> getSet(String hash, Long key) {
        Object value = strRedisTemplate.opsForHash().get(hash, key);
        if (value instanceof String json) {
            try {
                return mapper.readValue(json, new TypeReference<>() {
                });
            } catch (IOException e) {
                log.error("io ex message: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return new HashSet<>();
    }

    @Override
    public Map<String, Set<Long>> getMap(String hash) {
        Map<String, Set<Long>> map = new HashMap<>();

        Map<Object, Object> entries = strRedisTemplate.opsForHash().entries(hash);
        entries.forEach((k, v) -> {
            if (k instanceof String k1 && v instanceof String v2) {
                try {
                    map.put(k1, mapper.readValue(v2, new TypeReference<>() {
                    }));
                } catch (IOException e) {
                    log.error("io ex message: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });
        return map;
    }

    @Override
    public void removeValues(String hash, Long... key) {
        strRedisTemplate.opsForHash().delete(hash, key);
    }
}
