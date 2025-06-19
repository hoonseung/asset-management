package com.sewon.security.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtBlackListServiceTest {


    @DisplayName("가져온 값을 제대로 파싱되는지")
    @Test
    void parse_isBlackListTokenTest() {
        String getValue = "token:abc123.enable:true";
        String[] values = getValue.split("\\.");
        String token = values[0].substring(values[0].indexOf(":") + 1);

        boolean enable = Boolean.parseBoolean(values[1].substring(values[1].indexOf(":") + 1));

        Assertions.assertNotNull(token);
        Assertions.assertTrue(enable);
    }

}