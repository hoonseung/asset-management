package com.sewon.jpa.notification;

import com.sewon.account.model.Account;
import com.sewon.notification.model.Notification;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {


    List<Notification> findAllByAccountIdAndIsRead(Long accountId, boolean isRead);

    @Query("select ac from Account ac where ac.affiliation.id = :affiliationId")
    List<Account> findAllByAccountByAffiliationId(Long affiliationId);

    @Transactional
    @Query("update Notification n set n.isRead = :isRead, n.updatedDate = current timestamp where n.id in :ids")
    @Modifying
        // jpa auditing trigger x
    void updateIsReadByIdIn(Boolean isRead, Collection<Long> ids);
}
