package com.sewon.jpa.notification;

import com.sewon.account.model.Account;
import com.sewon.notification.model.Notification;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {


    List<Notification> findAllByAccountIdAndIsRead(Long accountId, boolean isRead);

    @Query("select ac from Account ac where ac.affiliation.id = :affiliationId")
    List<Account> findAllByAccountByAffiliationId(Long affiliationId);

    @Query("update Notification n set n.isRead = :isRead where n.id in :ids")
    @Modifying
    void updateIsReadByIdIn(Boolean isRead, Collection<Long> ids);
}
