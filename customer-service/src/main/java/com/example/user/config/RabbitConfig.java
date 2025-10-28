package com.example.user.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "dataQueue";

    public static final String QUEUE_NAME2 = "product-queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME2, true);
    }
}
