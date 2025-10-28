package com.example.user.controller;

import com.example.user.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/send")
public class ProducerController {

    private final RabbitTemplate rabbitTemplate;

    public ProducerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public String sendMessage(@RequestBody MessageRequest request) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, request.getMessage());
        return "✅ 메시지 전송 완료: " + request.getMessage();
    }

    static class MessageRequest {
        private String message;
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
