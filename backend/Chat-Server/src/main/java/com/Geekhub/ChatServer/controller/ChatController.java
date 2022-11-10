package com.Geekhub.ChatServer.controller;

import com.Geekhub.ChatServer.constant.KafkaConstants;
import com.Geekhub.ChatServer.model.Message;
import com.Geekhub.ChatServer.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    private final ChatService chatService;

    @PostMapping(value = "/publish")
    public void sendMessage(@RequestBody Message message) {
        log.info("Produce message : " + message.toString());
        message.setTimestamp(LocalDateTime.now().toString());
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/chat/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        return message;
    }

    @GetMapping("/findRoom")
    public void FindRoom(@RequestParam String roomId){

        log.info("here findroom");
        List<Message>Senders = chatService.FindPeopleByRoomId(roomId);
    }

}