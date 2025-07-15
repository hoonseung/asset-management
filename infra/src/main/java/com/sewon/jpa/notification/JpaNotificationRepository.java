package com.sewon.jpa.notification;

import com.sewon.account.model.Account;
import com.sewon.notification.model.Notification;
import com.sewon.notification.repository.NotificationRepository;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaNotificationRepository implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public void save(Notification notification) {
        notificationJpaRepository.save(notification);
    }

    @Override
    public List<Notification> saveAll(List<Notification> notifications) {
        return notificationJpaRepository.saveAll(notifications);
    }

    @Override
    public List<Notification> findAllByAccountId(Long accountId) {
        return notificationJpaRepository.findAllByAccountIdAndIsRead(accountId, false);
    }

    @Override
    public List<Account> findAllAccountByAffiliationId(Long affiliationId) {
        return notificationJpaRepository.findAllByAccountByAffiliationId(affiliationId);
    }

    public void readUpdateByIds(Collection<Long> ids) {
        notificationJpaRepository.updateIsReadByIdIn(true, ids);
    }
}
