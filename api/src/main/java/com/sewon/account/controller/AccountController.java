package com.sewon.account.controller;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.account.request.AccountLoginRequest;
import com.sewon.account.request.AccountRegistrationRequest;
import com.sewon.account.response.AccountLoginResponse;
import com.sewon.account.response.RefreshTokenResponse;
import com.sewon.common.response.ApiResponse;
import com.sewon.security.application.JwtBlackListService;
import com.sewon.security.model.auth.AccessToken;
import com.sewon.security.model.auth.AuthUser;
import com.sewon.security.model.auth.RefreshToken;
import com.sewon.security.service.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/account")
@RestController
public class AccountController {

    private final AccountService accountService;
    private final JwtTokenHandler jwtTokenHandler;
    private final JwtBlackListService jwtBlackListService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerAccount(
        @RequestBody AccountRegistrationRequest request) {
        accountService.registerAccount(request.toAccount(), request.department(),
            request.corporation());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountLoginResponse>> loginAccount(
        @RequestBody AccountLoginRequest request) {
        Account account = accountService.loginAccount(request.username(), request.password());
        AccessToken accessToken = jwtTokenHandler.generateAccessToken(account.getId(),
            account.getUsername(),
            account.getRole().name());
        RefreshToken refreshToken = jwtTokenHandler.generateRefreshToken(account.getId(),
            account.getUsername(),
            account.getRole().name());

        jwtBlackListService.saveRefreshToken(refreshToken.token(), account.getId(), false,
            refreshToken.expiration());

        AccountLoginResponse response = AccountLoginResponse.from(account, accessToken,
            refreshToken);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logoutAccount(
        @AuthenticationPrincipal AuthUser authUser) {
        AccessToken accessToken = authUser.getAccessToken();
        accountService.logoutAccount(accessToken.token(), authUser.getId(),
            accessToken.expiration());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/auth/token-refresh")
    public ResponseEntity<ApiResponse<Object>> tokenRefresh(
        @RequestHeader("Authorization-a") String headerAccessToken,
        @RequestHeader("Authorization-r") String refreshHeaderToken) {
        String accessHeaderToken =
            headerAccessToken.isBlank() ? "" : headerAccessToken;

        if (jwtTokenHandler.isValidRefreshToken(refreshHeaderToken) && accountService
            .isEnableRefreshToken(accessHeaderToken, refreshHeaderToken,
                jwtTokenHandler.getId(refreshHeaderToken),
                jwtTokenHandler.getAccessToken(accessHeaderToken).expiration())) {

            Account account = accountService.findAccountById(jwtTokenHandler.getId(
                refreshHeaderToken));
            AccessToken accessToken = jwtTokenHandler.generateAccessToken(account.getId(),
                account.getUsername(),
                account.getRole().name());
            RefreshToken refreshToken = jwtTokenHandler.generateRefreshToken(account.getId(),
                account.getUsername(),
                account.getRole().name());

            RefreshTokenResponse response = RefreshTokenResponse.from(accessToken, refreshToken);

            return ResponseEntity.ok(ApiResponse.ok(response));
        }
        return ResponseEntity.ok(ApiResponse.fail());
    }
}
