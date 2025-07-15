package com.sewon.notification.application;

import static com.sewon.notification.constant.MessageRoutingKey.READ_CHECK_KEY;

import com.sewon.notification.dto.NotificationResult;
import com.sewon.notification.repository.NotificationCacheRepository;
import com.sewon.notification.repository.NotificationRepository;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationCacheRepository notificationCacheRepository;

    private final NotificationProducer readCheckProducer;

    public NotificationService(NotificationRepository notificationRepository,
        NotificationCacheRepository notificationCacheRepository,
        @Qualifier("readCheckProducer")
        NotificationProducer readCheckProducer) {
        this.notificationRepository = notificationRepository;
        this.notificationCacheRepository = notificationCacheRepository;
        this.readCheckProducer = readCheckProducer;
    }

    public List<NotificationResult> getNotifications(Long accountId) {
        String key = "notify." + accountId;

        List<NotificationResult> results = notificationCacheRepository.get
            (key);
        if (!results.isEmpty()) {
            return results;
        }

        List<NotificationResult> notificationResults = notificationRepository.findAllByAccountId(
                accountId)
            .stream()
            .map(NotificationResult::from)
            .toList();

        notificationCacheRepository.save(key, notificationResults, Duration.ofMinutes(30));

        return notificationResults;
    }

    // 읽음처리 메세지 전송
    public void markRead(Long accountId, Long notificationId) {
        String key = "notify." + accountId;
        List<NotificationResult> results = notificationCacheRepository.get(key);
        results.stream()
            .filter(it -> it.getId().equals(notificationId))
            .forEach(it -> it.setRead(true));

        notificationCacheRepository.save(key, results, Duration.ofMinutes(30));

        String message = accountId + "." + notificationId;
        readCheckProducer.sendingNotification(
            message,
            READ_CHECK_KEY.getKey()
        );
    }
}
