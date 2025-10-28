package com.example.user.controller;

import com.example.user.model.Message;
import com.example.user.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public List<Message> list() {
        return service.findAll();
    }

    @PostMapping
    public Message add(@RequestBody Message message) {
        return service.save(message);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public Message update(@PathVariable Long id, @RequestBody Message message) {
        // 요청 바디의 데이터로 기존 고객 수정
        message.setId(id);
        return service.save(message);
    }
}
