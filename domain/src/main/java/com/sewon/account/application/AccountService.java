package com.sewon.account.application;

import static com.sewon.account.exception.AccountErrorCode.USER_DUPLICATED;
import static com.sewon.account.exception.AccountErrorCode.USER_NOT_FOUND;
import static com.sewon.account.exception.AccountErrorCode.USER_PW_INVALID;

import com.sewon.account.model.Account;
import com.sewon.account.repository.AccountRepository;
import com.sewon.affiliation.application.AffiliationService;
import com.sewon.affiliation.model.Affiliation;
import com.sewon.common.exception.DomainException;
import com.sewon.security.application.JwtBlackListService;
import com.sewon.security.application.PasswordHasher;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final JwtBlackListService jwtBlackListService;
    private final AffiliationService affiliationService;

    @Transactional
    public void registerAccount(Account account, String department, String corporation) {
        if (accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            Affiliation affiliation = affiliationService.findAffiliationByDepartmentAndCorporation(
                department, corporation);
            account.setAffiliation(affiliation);
            account.passwordEncrypting(PasswordHasher.hash(account.getPassword()));
            accountRepository.save(account);
            return;
        }
        throw new DomainException(USER_DUPLICATED);
    }

    public Account loginAccount(String username, String rawPassword) {
        Account account = findAccountByUsername(username);
        if (PasswordHasher.matches(rawPassword, account.getPassword())) {
            return account;
        }
        throw new DomainException(USER_PW_INVALID);
    }

    public void logoutAccount(String token, long id, Duration accessExpiration) {
        jwtBlackListService.blacklistAccessToken(token, id, true, accessExpiration);
        jwtBlackListService.blacklistRefreshToken(id);
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new DomainException(USER_NOT_FOUND));
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username)
            .orElseThrow(() -> new DomainException(USER_NOT_FOUND));
    }

    public boolean isEnableRefreshToken(String accessToken, String refreshToken, Long id,
        Duration accessExpire) {
        if (jwtBlackListService.isBlacklistToken(refreshToken, id, "refresh")) {
            return false;
        }
        if (StringUtils.hasText(accessToken)) {
            jwtBlackListService.blacklistAccessToken(accessToken, id, true, accessExpire);
        }
        jwtBlackListService.blacklistRefreshToken(id);
        return true;
    }
}
