package com.sewon.notification.repository;

import com.sewon.notification.dto.NotificationResult;
import java.time.Duration;
import java.util.List;

public interface NotificationCacheRepository {

    void save(String key, List<NotificationResult> values, Duration ttl);

    List<NotificationResult> get(String key);

    void delete(String key);

}
