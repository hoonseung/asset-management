package com.sewon.message.rental;

import static com.sewon.config.RabbitMQConfig.RENTAL_EXPIRE_QUEUE;
import static com.sewon.notification.constant.NotifyMessage.RENTAL_CHECK_EXPIRATION;

import com.sewon.jpa.notification.JpaNotificationRepository;
import com.sewon.message.notification.AbstractConsumer;
import com.sewon.message.repository.SseEmitterRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RentalExpireConsumer extends AbstractConsumer {

    public RentalExpireConsumer(
        JpaNotificationRepository jpaNotificationRepository,
        SseEmitterRepository sseEmitterRepository) {
        super(jpaNotificationRepository, sseEmitterRepository);
    }

    @Transactional
    @RabbitListener(queues = RENTAL_EXPIRE_QUEUE)
    public void doConsumeNotify(String message) {
        if (message != null) {
            String[] split = message.split("\\.");
            String barcode = split[0];
            Long affiliationId = Long.valueOf(split[1]);

            notifyToAccount(affiliationId,
                RENTAL_CHECK_EXPIRATION.formating(barcode));
        }
    }


}
