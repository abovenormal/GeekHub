package com.Geekhub.ChatServer.consumer;

import com.Geekhub.ChatServer.constant.KafkaConstants;
import com.Geekhub.ChatServer.dto.MessageDto;
import com.Geekhub.ChatServer.model.Message;
import com.Geekhub.ChatServer.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    private MessageRepository repository;


    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(MessageDto messageDto) {
        log.info("sending via kafka listener..");
        System.out.println(messageDto);
        Message message = Message.builder().content(messageDto.getContent()).roomId(messageDto.getRoomId()).sender(messageDto.getSender()).timestamp(messageDto.getTimestamp()).build();
        repository.save(message);
        String roomId =  message.getRoomId();
//        template.convertAndSend("/chat/test/", message);
        template.convertAndSend("/chat/" + roomId , message);
    }
}