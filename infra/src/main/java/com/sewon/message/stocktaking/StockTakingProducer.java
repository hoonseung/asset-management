package com.sewon.message.stocktaking;


import com.sewon.config.RabbitMQConfig;
import com.sewon.notification.application.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockTakingProducer implements NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendingNotification(Object message, String routingKey) {
        if (message instanceof String) {
            String value = String.valueOf(message);
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                routingKey,
                value
            );
        }
    }
}
