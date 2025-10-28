package com.example.user.service;

import com.example.user.config.RabbitConfig;
import com.example.user.model.CustomerMessage;
import com.example.user.repository.CustomerMessageRepository;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProductMessageConsumer {

    private final CustomerMessageRepository repository;
    private final Gson gson = new Gson();

    public ProductMessageConsumer(CustomerMessageRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME2)
    public void receiveMessage(String jsonMessage) {
        CustomerMessage message = gson.fromJson(jsonMessage, CustomerMessage.class);

        // ✅ messageId 중복 방지
        if (repository.existsByMessageId(message.getMessageId())) {
            System.out.println("⚠️ Duplicate message skipped: " + message.getMessageId());
            return; // 이미 저장된 메시지면 무시
        }

        repository.save(message);
        System.out.println("✅ Saved message: " + message.getName());
    }
}

