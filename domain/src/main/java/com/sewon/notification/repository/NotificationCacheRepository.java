package com.sewon.notification.repository;

import com.sewon.notification.dto.NotificationResult;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NotificationCacheRepository {

    void save(String key, List<NotificationResult> values, Duration ttl);

    List<NotificationResult> get(String key);

    void delete(String key);

    void put(String hash, Long key, Object value);

    Set<Long> getSet(String hash, Long key);

    Map<String, Set<Long>> getMap(String hash);

    void removeValues(String hash, String... key);

}
