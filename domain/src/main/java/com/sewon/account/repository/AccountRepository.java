package com.sewon.account.repository;

import com.sewon.account.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(Long id);

    Optional<Account> findByUsername(String username);

    List<Account> findAll();
}
