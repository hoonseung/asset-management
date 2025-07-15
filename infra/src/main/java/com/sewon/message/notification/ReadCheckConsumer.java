package com.sewon.message.notification;

import static com.sewon.config.RabbitMQConfig.RENTAL_EXPIRE_QUEUE;

import com.sewon.jpa.notification.JpaNotificationRepository;
import com.sewon.message.repository.SseEmitterRepository;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReadCheckConsumer extends AbstractConsumer {

    private final Map<Long, Set<Long>> notificationUpdateStore = new ConcurrentHashMap<>();

    public ReadCheckConsumer(JpaNotificationRepository jpaNotificationRepository,
        SseEmitterRepository sseEmitterRepository) {
        super(jpaNotificationRepository, sseEmitterRepository);
    }

    @Transactional
    @RabbitListener(queues = RENTAL_EXPIRE_QUEUE)
    public void doConsumeNotify(String message) {
        if (message != null) {
            String[] split = message.split("\\.");
            Long accountId = Long.valueOf(split[0]);
            Long notificationId = Long.valueOf(split[1]);

            // accountId로 조회가 안되면 accountId를 키로 해서 put
            Set<Long> notifyList = notificationUpdateStore.computeIfAbsent(
                accountId, key -> {
                    Set<Long> ids = new HashSet<>();
                    ids.add(notificationId);
                    return ids;
                });

            notifyList.add(notificationId);
        }

    }

    // 30분마다 notificationUpdateStore를 flush하는 스케줄러
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void flushNotificationUpdateStore() {
        notificationUpdateStore.forEach((accountId, notifyList) -> {
            if (!notifyList.isEmpty()) {
                jpaNotificationRepository.readUpdateByIds(notifyList);
                notificationUpdateStore.put(accountId, new HashSet<>());
            }
        });
    }


}
