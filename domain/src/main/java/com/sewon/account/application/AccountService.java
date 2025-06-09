package com.sewon.account.application;

import static com.sewon.account.exception.AccountErrorCode.USER_DUPLICATED;
import static com.sewon.account.exception.AccountErrorCode.USER_NOT_FOUND;

import com.sewon.account.model.Account;
import com.sewon.account.repository.AccountRepository;
import com.sewon.common.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void registerAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            accountRepository.save(account);
            return;
        }
        throw new DomainException(USER_DUPLICATED);
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new DomainException(USER_NOT_FOUND));
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username)
            .orElseThrow(() -> new DomainException(USER_NOT_FOUND));
    }
}
