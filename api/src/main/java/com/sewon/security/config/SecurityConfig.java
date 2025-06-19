package com.sewon.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.sewon.security.exception.TokenAccessDeniedHandler;
import com.sewon.security.exception.TokenEntryPoint;
import com.sewon.security.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                request -> request.requestMatchers("/account/login", "/account/register",
                        "/account/auth/token-refresh").permitAll()
                    .requestMatchers(HttpMethod.POST,
                        "/affiliations", "/locations", "/asset-types", "/corporations")
                    .permitAll()
                    .anyRequest().authenticated())
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new TokenEntryPoint())
                .accessDeniedHandler(new TokenAccessDeniedHandler())
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
