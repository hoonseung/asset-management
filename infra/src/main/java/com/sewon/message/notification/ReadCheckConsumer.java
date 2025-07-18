package com.sewon.message.notification;

import static com.sewon.config.RabbitMQConfig.NOTIFY_READ_QUEUE;

import com.sewon.jpa.notification.JpaNotificationRepository;
import com.sewon.message.repository.SseEmitterRepository;
import com.sewon.notification.repository.NotificationCacheRepository;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ReadCheckConsumer extends AbstractConsumer {

    private final Map<Long, Set<Long>> notificationUpdateStore = new ConcurrentHashMap<>();
    private final NotificationCacheRepository notificationCacheRepository;

    public ReadCheckConsumer(JpaNotificationRepository jpaNotificationRepository,
        NotificationCacheRepository notificationCacheRepository,
        SseEmitterRepository sseEmitterRepository) {
        super(jpaNotificationRepository, sseEmitterRepository);
        this.notificationCacheRepository = notificationCacheRepository;
    }


    @RabbitListener(queues = NOTIFY_READ_QUEUE)
    public void doConsumeNotify(String message) {
        if (message != null) {
            String[] split = message.split("\\.");
            Long accountId = Long.valueOf(split[0]);
            Long notificationId = Long.valueOf(split[1]);

            String hashKey = "readCheck";
            Set<Long> ids = notificationCacheRepository.getSet(hashKey,
                accountId);
            ids.add(notificationId);

            notificationCacheRepository.put(hashKey, accountId, ids);
        }

    }

    // 앱 시작 후 30분뒤 첫 호출 이후 주기는 30분
    @Transactional
    @Scheduled(initialDelay = 10 * 60 * 1000, fixedRate = 30 * 60 * 1000)
    public void flushNotificationUpdateStore() {
        log.info("invoked flushNotificationUpdateStore method");

        String hashKey = "readCheck";

        notificationCacheRepository.getMap(hashKey).forEach((accountId, notifyList) -> {
            if (!notifyList.isEmpty()) {
                jpaNotificationRepository.readUpdateByIds(notifyList);
                notificationCacheRepository.removeValues(hashKey, accountId);
                log.info("finished accountId: {} flushNotificationUpdate", accountId);
            }
        });
    }


}
