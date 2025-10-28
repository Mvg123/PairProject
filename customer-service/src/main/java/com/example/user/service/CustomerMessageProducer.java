package com.example.user.service;

import com.example.user.config.RabbitConfig;
import com.example.user.model.CustomerMessage;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public CustomerMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(CustomerMessage message) {
        String json = gson.toJson(message);
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME2, json);
    }
}

