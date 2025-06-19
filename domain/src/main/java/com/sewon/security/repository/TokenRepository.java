package com.sewon.security.repository;

import java.time.Duration;

public interface TokenRepository {

    void save(String key, String value, Duration ttl);

    String get(String key);

    Duration getExpiration(String key);
}
