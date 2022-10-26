package com.GeekHub.KafkaServer.consumer;

import com.GeekHub.KafkaServer.constant.KafkaConstants;
import com.GeekHub.KafkaServer.model.Message;
import com.GeekHub.KafkaServer.repository.MessageRepository;
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
    public void listen(Message message) {
        log.info("sending via kafka listener..");
        System.out.println(message);
        repository.save(message);
        //
        //db저장
        //topic/group 로그데이터 보낼 경로 설정해야함
        //template.convertAndSend("/topic/group", message);
    }
}