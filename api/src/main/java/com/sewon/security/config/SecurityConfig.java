package com.sewon.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.sewon.security.exception.TokenAccessDeniedHandler;
import com.sewon.security.exception.TokenEntryPoint;
import com.sewon.security.filter.TokenAuthenticationFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                request -> request.requestMatchers("/account/login"
                        , "/account/register", "/notifications/connect/**",
                        "/account/auth/token-refresh").permitAll()
                    .requestMatchers(HttpMethod.GET,
                        "/affiliations", "/locations", "/asset-types", "/corporations")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST,
                        "/affiliations", "/locations", "/asset-types", "/corporations")
                    .permitAll()
                    .anyRequest().authenticated())
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new TokenEntryPoint())
                .accessDeniedHandler(new TokenAccessDeniedHandler())
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }


    /**
     * 사용자 인증 관련 설정 명시 스프링 사용자 비밀번호 자동 생성 생략하기 위한 코드
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(
            List.of("http://localhost:3000", "https://localhost:3000",
                "http://192.168.0.213:3000", "http://192.168.9.197:3000",
                "http://211.181.221.197:3000", "https://192.168.9.197:3000",
                "https://211.181.221.197:3000")); // 클라이언트 브라우저 url
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(
            List.of("Authorization", "Authorization-a", "Authorization-r")); // 토큰 헤더 노출

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
