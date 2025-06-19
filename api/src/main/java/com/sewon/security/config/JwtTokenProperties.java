package com.sewon.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties {

    private String secret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
}
