package com.sewon.security.application;

import com.sewon.security.repository.TokenCacheRepository;
import com.sewon.security.repository.TokenRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class JwtBlackListService {

    private final TokenRepository tokenRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";
    private static final String VALUE_DELIMITER = ";";


    public void saveRefreshToken(String token, long id, Boolean isBlackList, Duration ttl) {
        String key = REFRESH + id;
        String value = getValue(token, isBlackList);
        tokenCacheRepository.saveRt(key, token);
        tokenRepository.save(key, value, ttl);
    }

    public void blacklistAccessToken(String token, long id, Boolean isBlackList, Duration ttl) {
        String key = ACCESS + id;
        String value = getValue(token, isBlackList);
        tokenCacheRepository.saveAt(key, value);
        tokenRepository.save(key, value, ttl);
    }

    public void blacklistRefreshToken(long id) {
        String key = REFRESH + id;
        String value;
        try {
            value = tokenRepository.get(key);
            if (value == null) {
                return;
            }
            value = getValue(getToken(value), true);
            Duration expiration = tokenRepository.getExpiration(key);
            tokenRepository.save(key, value, expiration);
        } catch (Exception e) {
            value = tokenCacheRepository.getRt(key);
            value = getValue(getToken(value), true);
            tokenCacheRepository.saveRt(key, value);
        }
    }

    public boolean isBlacklistToken(String token, long id, String type) {
        String key = type + id;
        String value;
        String tokenPs = null;
        boolean isBlackList = false;
        try {
            value = tokenRepository.get(key);
            if (value == null) {
                return false;
            }
            tokenPs = getToken(value);
            isBlackList = getIsBlackList(value);
        } catch (Exception e) {
            if (type.equals(ACCESS)) {
                value = tokenCacheRepository.getAt(key);
                tokenPs = getToken(value);
                isBlackList = getIsBlackList(value);
            } else if (type.equals(REFRESH)) {
                value = tokenCacheRepository.getRt(key);
                tokenPs = getToken(value);
                isBlackList = getIsBlackList(value);
            }
        }
        return StringUtils.hasText(tokenPs) && token.equals(tokenPs) && isBlackList;
    }

    private String getValue(String token, Boolean isBlackList) {
        String tokenValue = "token:" + token;
        String enableValue = "isBlackList:" + isBlackList;
        return tokenValue + VALUE_DELIMITER + enableValue;
    }

    private String getToken(String value) {
        String[] values = value.split(VALUE_DELIMITER);
        return values[0].substring(values[0].indexOf(":") + 1);
    }

    private boolean getIsBlackList(String value) {
        String[] values = value.split(VALUE_DELIMITER);
        return Boolean.parseBoolean(values[1].substring(values[1].indexOf(":") + 1));
    }
}
