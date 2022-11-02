package com.GeekHub.KafkaServer.controller;

import com.GeekHub.KafkaServer.constant.KafkaConstants;
import com.GeekHub.KafkaServer.model.Message;
import com.GeekHub.KafkaServer.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/location")
public class LogController {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;


    @PostMapping(value = "/sendLog")
    public void sendMessage(@RequestBody Message message) {
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}