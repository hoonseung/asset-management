package com.sewon.jpa.account;

import com.sewon.account.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a join fetch a.affiliation af join fetch af.corporation where a.username = :username")
    Optional<Account> findByUsername(@Param("username") String username);
}
