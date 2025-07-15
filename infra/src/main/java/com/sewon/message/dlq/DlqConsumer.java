package com.sewon.message.dlq;

import com.sewon.config.RabbitMQConfig;
import com.sewon.notification.application.NotificationProducer;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DlqConsumer {

    private final NotificationProducer rentalProducer;
    private final NotificationProducer stockTakingProducer;
    private final NotificationProducer readCheckProducer;


    @RabbitListener(queues = RabbitMQConfig.RENTAL_DEAD_LETTER_QUEUE)
    public void handleRentalDeadLetter(String message, Message rawMessage) {
        handleRetry(message, rawMessage, rentalProducer);
    }


    @RabbitListener(queues = RabbitMQConfig.STOCK_TAKING_DEAD_LETTER_QUEUE)
    public void handleStockTakingDeadLetter(String message, Message rawMessage) {
        handleRetry(message, rawMessage, stockTakingProducer);
    }


    @RabbitListener(queues = RabbitMQConfig.NOTIFY_READ_DEAD_LETTER_QUEUE)
    public void handleReadCheckDeadLetter(String message, Message rawMessage) {
        handleRetry(message, rawMessage, readCheckProducer);
    }


    private void handleRetry(String message, Message rawMessage,
        NotificationProducer producer) {
        if (message != null && !message.isBlank()) {
            log.error("dlq receive message: {}", message);
            try {
                MessageProperties properties = rawMessage.getMessageProperties();
                producer.sendingNotification(message, properties.getReceivedRoutingKey());
                // 필요 시 실패 이유나 헤더 확인
                List<Map<String, Object>> xDeath = (List<Map<String, Object>>) properties.getHeaders()
                    .get("x-death");
                Map<String, Object> deathInfo = xDeath.getFirst();
                if (deathInfo != null && !deathInfo.isEmpty()) {
                    String reason = (String) deathInfo.get("reason");
                    Long count = (Long) deathInfo.get("count");
                    log.error("fail reason: {}", reason);
                    log.error("retry count: {}", count);
                    if (count != null && count > 2) {
                        log.error("dlq retry count over");
                        throw new RuntimeException("dlq retry count over");
                    }
                }
            } catch (Exception e) {
                log.error("rental dlq retry ex message: {}", e.getMessage());
                return;
            }
        }
        log.error("dlq handleRetry invoked but message is null");
    }

}
