package com.sewon.account.application;

import com.sewon.account.model.Account;
import com.sewon.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
