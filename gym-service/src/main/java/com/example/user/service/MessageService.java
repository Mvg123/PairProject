package com.example.user.service;

import com.example.user.config.RabbitConfig;
import com.example.user.model.Gym;
import com.example.user.model.Message;
import com.example.user.repository.GymRepository;
import com.example.user.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) { this.messageRepository = messageRepository; }

    public List<Message> findAll() {return messageRepository.findAll(); }

    public Message save(Message message) { return messageRepository.save(message); }

    public void delete(Long id) { messageRepository.deleteById(id); }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(String message){
        System.out.println("받은 메세지: " + message);
        Message messageEntity = new Message();
        messageEntity.setContent(message);
        messageRepository.save(messageEntity);
        System.out.println("💾 DB 저장 완료");
    }
}
