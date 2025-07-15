package com.sewon.security.filter;

import com.sewon.security.model.auth.AuthUser;
import com.sewon.security.service.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtTokenHandler provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
        } else if (!provider.isValidAccessToken(token)) {
            filterChain.doFilter(request, response);
        } else {
            AuthUser authUser = getAuthUser(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authUser, null, authUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));

            SecurityContextHolder.getContextHolderStrategy()
                .getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private AuthUser getAuthUser(String token) {
        return new AuthUser(
            provider.getId(token),
            provider.getUsername(token),
            provider.getRoles(token),
            provider.getAccessToken(token)
        );
    }
}
