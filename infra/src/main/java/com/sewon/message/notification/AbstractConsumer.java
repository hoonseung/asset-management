package com.sewon.message.notification;

import com.sewon.account.model.Account;
import com.sewon.jpa.notification.JpaNotificationRepository;
import com.sewon.message.dto.NotificationEmitterDto;
import com.sewon.message.repository.SseEmitterRepository;
import com.sewon.notification.application.AccountNotifier;
import com.sewon.notification.dto.NotificationResult;
import com.sewon.notification.model.Notification;
import com.sewon.redis.notification.CacheNotificationRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public abstract class AbstractConsumer implements AccountNotifier {

    protected final JpaNotificationRepository jpaNotificationRepository;
    private final SseEmitterRepository sseEmitterRepository;

    @Autowired
    private CacheNotificationRepository cacheNotificationRepository;


    @Override
    public void notifyToAccount(Long affiliationId, String message) {
        log.info("given affiliation id: {}", affiliationId);
        List<Account> accounts = jpaNotificationRepository.findAllAccountByAffiliationId(
            affiliationId);

        List<Notification> notifications = accounts.stream()
            .map(account -> Notification.of(message,
                LocalDateTime.now(), account))
            .toList();

        List<Notification> notificationsPs = jpaNotificationRepository.saveAll(notifications);

        notificationsPs.forEach(notify -> {
                Long accountId = notify.getAccount().getId();
                sseEmitterRepository.sendTo(accountId,
                    new NotificationEmitterDto(message,
                        LocalDateTime.now()));

                // 알림 생성 후 다시 생성된 걸로 업데이트 되어야 하니까 기존 캐시를 무효화 후
                // 업데이트 된 알림 목록을 바로 캐시 저장하여 알람 목록 조회 때 바로 캐시에서 가져올 수 있게 함
                // 이렇게 한 이유는 알람 목록 조회는 실시간 알람보다 이벤트가 더 많기 때문
                String cacheKey = "notify." + accountId;
                List<NotificationResult> results = cacheNotificationRepository.get(cacheKey);
                results.addAll(notificationsPs.stream()
                    .filter(it -> accountId.equals(it.getAccount().getId()))
                    .map(NotificationResult::from).toList());

                cacheNotificationRepository.delete(cacheKey);
                cacheNotificationRepository.save(cacheKey, results, Duration.ofMinutes(30));
            }
        );
    }


}
