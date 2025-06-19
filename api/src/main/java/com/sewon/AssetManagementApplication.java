package com.sewon;

import com.sewon.config.CaffeineCacheProperties;
import com.sewon.security.config.JwtTokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JwtTokenProperties.class, CaffeineCacheProperties.class})
@SpringBootApplication
public class AssetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetManagementApplication.class);
    }
}
