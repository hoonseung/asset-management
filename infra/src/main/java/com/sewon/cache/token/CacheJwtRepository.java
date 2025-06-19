package com.sewon.cache.token;

import com.github.benmanes.caffeine.cache.Cache;
import com.sewon.security.repository.TokenCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CacheJwtRepository implements TokenCacheRepository {

    private final Cache<String, String> accessTokenBlackListCache;
    private final Cache<String, String> refreshTokenBlackListCache;

    @Override
    public void saveAt(String key, String value) {
        accessTokenBlackListCache.put(key, value);
    }

    @Override
    public void saveRt(String key, String value) {
        refreshTokenBlackListCache.put(key, value);
    }

    @Override
    public String getAt(String key) {
        return accessTokenBlackListCache.getIfPresent(key);
    }

    @Override
    public String getRt(String key) {
        return refreshTokenBlackListCache.getIfPresent(key);
    }
}
