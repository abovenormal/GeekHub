package com.GeekHub.KafkaServer.service;

import com.GeekHub.KafkaServer.model.Message;
import com.GeekHub.KafkaServer.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository repository;

    public Message getMessage(String driver){
        Message message = new Message();
        List<Message> messages = repository.getMessage(driver);
        System.out.println("service-check");
        message = messages.get(0);
        return message;
    }
}
