package com.sewon.jpa.account;

import com.sewon.account.model.Account;
import com.sewon.account.repository.AccountRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepository implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountJpaRepository.findById(id);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountJpaRepository.findByUsername(username);
    }

    @Override
    public List<Account> findAll() {
        return accountJpaRepository.findAll();
    }
}
