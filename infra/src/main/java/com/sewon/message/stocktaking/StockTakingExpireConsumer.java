package com.sewon.message.stocktaking;

import static com.sewon.config.RabbitMQConfig.STOCK_TAKING_EXPIRE_QUEUE;
import static com.sewon.notification.constant.MessageRoutingKey.STOCK_TAKING_LOCATION_EXPIRATION_KEY;
import static com.sewon.notification.constant.NotifyMessage.STOCK_TAKING_LOCATION_EXPIRATION;

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
public class StockTakingExpireConsumer extends AbstractConsumer {

    private final ConcurrentHashMap<String, NotifyMessage> messageStore = new ConcurrentHashMap<>(
        Map.of(
            STOCK_TAKING_LOCATION_EXPIRATION_KEY.getKey(), STOCK_TAKING_LOCATION_EXPIRATION
        )
    );

    public StockTakingExpireConsumer(
        JpaNotificationRepository jpaNotificationRepository,
        SseEmitterRepository sseEmitterRepository) {
        super(jpaNotificationRepository, sseEmitterRepository);
    }

    @Transactional
    @RabbitListener(queues = STOCK_TAKING_EXPIRE_QUEUE)
    public void doConsumeNotify(String message, Message amqpMessage) {
        String routingKey = amqpMessage.getMessageProperties().getReceivedRoutingKey();

        if (routingKey != null && message != null) {
            String[] split = message.split("\\.");
            int day = Integer.parseInt(split[0]);
            String location = split[1];
            Long affiliationId = Long.valueOf(split[2]);

            notifyToAccount(affiliationId,
                messageStore.get(routingKey).formating(location, day));
        }
    }
}
