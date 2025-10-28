package com.example.user.controller;


import com.example.user.model.CustomerMessage;
import com.example.user.repository.CustomerMessageRepository;
import com.example.user.service.CustomerMessageProducer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cusmsg")
public class CustomerMessageRestController {

    private final CustomerMessageProducer producer;
    private final CustomerMessageRepository repository;

    public CustomerMessageRestController(CustomerMessageProducer producer, CustomerMessageRepository repository) {
        this.producer = producer;
        this.repository = repository;
    }

    // 모든 메시지 조회
    @GetMapping
    public List<CustomerMessage> getAllMessages() {
        return repository.findAll();
    }

    // 메시지 전송
    @PostMapping
    public CustomerMessage sendMessage(@RequestBody CustomerMessage message) {
        producer.sendMessage(message);
        return repository.save(message); // DB에도 저장
    }
}

