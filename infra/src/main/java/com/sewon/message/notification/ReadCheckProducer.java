package com.sewon.message.notification;

import com.sewon.config.RabbitMQConfig;
import com.sewon.notification.application.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadCheckProducer implements NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendingNotification(Object message, String routingKey) {
        if (message instanceof Long id) {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                routingKey,
                String.valueOf(id)
            );
        }
    }
}
