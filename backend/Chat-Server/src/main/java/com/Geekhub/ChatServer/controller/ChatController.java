package com.Geekhub.ChatServer.controller;

import com.Geekhub.ChatServer.client.AdminClient;
import com.Geekhub.ChatServer.constant.KafkaConstants;
import com.Geekhub.ChatServer.dto.GetRoomDto;
import com.Geekhub.ChatServer.dto.RoomDto;
import com.Geekhub.ChatServer.dto.UserInfoDto;
import com.Geekhub.ChatServer.model.Message;
import com.Geekhub.ChatServer.model.Room;
import com.Geekhub.ChatServer.service.ChatService;
import com.Geekhub.ChatServer.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final RoomService roomService;
    private final ChatService chatService;
    private final AdminClient adminClient;

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
        log.info("연결 테스트" + message.toString());
        message.setTimestamp(LocalDateTime.now().toString());
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @GetMapping("/room")
    public ResponseEntity<?> FindRoom(@RequestParam String userIdx, @RequestParam String dow, @RequestParam String partTime){
        GetRoomDto getRoomDto = new GetRoomDto();
        UserInfoDto userInfoDto = adminClient.userInfo(userIdx);
        getRoomDto.setDow(dow);
        getRoomDto.setLocalSchool(userInfoDto.getLocalSchool());
        getRoomDto.setPartTime(partTime);
        log.info(getRoomDto.toString());
        try {
            Room room = roomService.findRoom(getRoomDto);
            return ResponseEntity.status(HttpStatus.OK).body(room);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @PostMapping("/room")
    public ResponseEntity<?> makeRoom(@RequestBody RoomDto roomDto) {
        roomService.makeRoom(roomDto);
        return ResponseEntity.status(HttpStatus.OK).body("채팅방 생성");
    }

}