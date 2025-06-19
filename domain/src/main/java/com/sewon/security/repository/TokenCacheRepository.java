package com.sewon.security.repository;

public interface TokenCacheRepository {

    void saveAt(String key, String value);

    void saveRt(String key, String value);

    String getAt(String key);

    String getRt(String key);

}
