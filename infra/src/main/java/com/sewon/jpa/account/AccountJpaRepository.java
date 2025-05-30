package com.sewon.jpa.account;

import com.sewon.account.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}
