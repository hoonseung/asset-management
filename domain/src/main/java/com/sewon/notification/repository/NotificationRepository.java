package com.sewon.notification.repository;

import com.sewon.account.model.Account;
import com.sewon.notification.model.Notification;
import java.util.List;

public interface NotificationRepository {

    void save(Notification notification);

    List<Notification> saveAll(List<Notification> notifications);

    List<Notification> findAllByAccountId(Long accountId);

    List<Account> findAllAccountByAffiliationId(Long affiliationId);
}
