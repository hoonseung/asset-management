package com.sewon.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    //exchange
    public static final String EXCHANGE = "notification-exchange";
    public static final String DEAD_LETTER_EXCHANGE = "dlx-exchange";

    //dlx key
    public static final String RENTAL_DLX_REQUEST_KEY = "rental.dlx.request";
    public static final String RENTAL_DLX_EXPIRE_KEY = "rental.dlx.expire";
    public static final String STOCK_TAKING_DLX_EXPIRE_KEY = "stock-taking.dlx.expire";
    public static final String NOTIFY_DLX_READ_KEY = "notify.dlx.read";


    // queue
    public static final String RENTAL_REQUEST_QUEUE = "rental-request-queue";
    public static final String RENTAL_EXPIRE_QUEUE = "rental-expire-queue";
    public static final String STOCK_TAKING_EXPIRE_QUEUE = "stock-taking-queue";
    public static final String NOTIFY_READ_QUEUE = "notify-read-queue";

    // dlq
    public static final String RENTAL_DEAD_LETTER_QUEUE = "rental-dlx-queue";
    public static final String STOCK_TAKING_DEAD_LETTER_QUEUE = "stock-taking-dlx-queue";
    public static final String NOTIFY_READ_DEAD_LETTER_QUEUE = "notify-read-dlx-queue";


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue rentalRequestQueue() {
        return QueueBuilder.durable(RENTAL_REQUEST_QUEUE)
            .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
            .withArgument("x-dead-letter-routing-key", RENTAL_DLX_REQUEST_KEY)
            .build();
    }

    @Bean
    public Queue rentalExpireQueue() {
        return QueueBuilder.durable(RENTAL_EXPIRE_QUEUE)
            .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
            .withArgument("x-dead-letter-routing-key", RENTAL_DLX_EXPIRE_KEY)
            .build();
    }

    @Bean
    public Queue stockTakingExpireQueue() {
        return QueueBuilder.durable(STOCK_TAKING_EXPIRE_QUEUE)
            .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
            .withArgument("x-dead-letter-routing-key", STOCK_TAKING_DLX_EXPIRE_KEY)
            .build();
    }

    @Bean
    public Queue notifyReadQueue() {
        return QueueBuilder.durable(NOTIFY_READ_QUEUE)
            .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
            .withArgument("x-dead-letter-routing-key", NOTIFY_DLX_READ_KEY)
            .build();
    }

    @Bean
    public Queue rentalDeadLetterQueue() {
        return QueueBuilder.durable(RENTAL_DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Queue stokcTakingDeadLetterQueue() {
        return QueueBuilder.durable(STOCK_TAKING_DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Queue notifyReadDeadLetterQueue() {
        return QueueBuilder.durable(NOTIFY_READ_DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding rentalRequestBinding(Queue rentalRequestQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(rentalRequestQueue)
            .to(topicExchange)
            .with("rental.request.#");
    }

    @Bean
    public Binding rentalExpireBinding(Queue rentalExpireQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(rentalExpireQueue)
            .to(topicExchange)
            .with("rental.expire.#");
    }

    @Bean
    public Binding stockTakingExpireBinding(Queue stockTakingExpireQueue,
        TopicExchange topicExchange) {
        return BindingBuilder.bind(stockTakingExpireQueue)
            .to(topicExchange)
            .with("stock.taking.expire.#");
    }

    @Bean
    public Binding notifyReadBinding(Queue notifyReadQueue,
        TopicExchange topicExchange) {
        return BindingBuilder.bind(notifyReadQueue)
            .to(topicExchange)
            .with("notify.read.#");
    }


    @Bean
    public Binding rentalDeadLetterBinding() {
        return BindingBuilder
            .bind(rentalDeadLetterQueue())
            .to(deadLetterExchange())
            .with("rental.dlx.#");
    }

    @Bean
    public Binding stockTakingDeadLetterBinding() {
        return BindingBuilder
            .bind(stokcTakingDeadLetterQueue())
            .to(deadLetterExchange())
            .with("stock-taking.dlx.#");
    }

    @Bean
    public Binding notfyReadDeadLetterBinding() {
        return BindingBuilder
            .bind(notifyReadQueue())
            .to(deadLetterExchange())
            .with("notify.dlx.#");
    }


}
