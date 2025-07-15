package com.sewon.message.rental;

import static com.sewon.config.RabbitMQConfig.RENTAL_REQUEST_QUEUE;
import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_REQUEST_APPROVE_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_REQUEST_RECEIVED_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_REQUEST_REJECT_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RETURN_REQUEST_APPROVE_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RETURN_REQUEST_RECEIVED_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RETURN_REQUEST_REJECT_KEY;
import static com.sewon.notification.constant.NotifyMessage.RENTAL_REQUEST_APPROVE;
import static com.sewon.notification.constant.NotifyMessage.RENTAL_REQUEST_RECEIVED;
import static com.sewon.notification.constant.NotifyMessage.RENTAL_REQUEST_REJECT;
import static com.sewon.notification.constant.NotifyMessage.RETURN_REQUEST_APPROVE;
import static com.sewon.notification.constant.NotifyMessage.RETURN_REQUEST_RECEIVED;
import static com.sewon.notification.constant.NotifyMessage.RETURN_REQUEST_REJECT;

import com.sewon.jpa.notification.JpaNotificationRepository;
import com.sewon.message.notification.AbstractConsumer;
import com.sewon.message.repository.SseEmitterRepository;
import com.sewon.notification.constant.NotifyMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RentalRequestConsumer extends AbstractConsumer {

    private final ConcurrentHashMap<String, NotifyMessage> messageStore = new ConcurrentHashMap<>(
        Map.of(
            RENTAL_REQUEST_RECEIVED_KEY.getKey(), RENTAL_REQUEST_RECEIVED,
            RENTAL_REQUEST_APPROVE_KEY.getKey(), RENTAL_REQUEST_APPROVE,
            RENTAL_REQUEST_REJECT_KEY.getKey(), RENTAL_REQUEST_REJECT,

            RETURN_REQUEST_RECEIVED_KEY.getKey(), RETURN_REQUEST_RECEIVED,
            RETURN_REQUEST_REJECT_KEY.getKey(), RETURN_REQUEST_REJECT,

            RETURN_REQUEST_APPROVE_KEY.getKey(), RETURN_REQUEST_APPROVE)
    );

    public RentalRequestConsumer(
        JpaNotificationRepository jpaNotificationRepository,
        SseEmitterRepository sseEmitterRepository) {
        super(jpaNotificationRepository, sseEmitterRepository);
    }

    @Transactional
    @RabbitListener(queues = RENTAL_REQUEST_QUEUE)
    public void doRentalAndReturnConsumeNotify(String message, Message amqpMessage) {
        String routingKey = amqpMessage.getMessageProperties().getReceivedRoutingKey();

        if (routingKey != null && message != null) {
            notifyToAccount(Long.valueOf(message), messageStore.get(routingKey).getTemplate());
        }
    }


}
