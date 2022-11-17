package com.Geekhub.ChatServer.controller;

import com.Geekhub.ChatServer.client.AdminClient;
import com.Geekhub.ChatServer.constant.KafkaConstants;
import com.Geekhub.ChatServer.dto.MessageDto;
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
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
//    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RoomService roomService;
    private final ChatService chatService;
    private final AdminClient adminClient;

//    @PostMapping(value = "/publish")
//    public void sendMessage(@RequestBody Message message) {
//        log.info("Produce message : " + message.toString());
//        message.setTimestamp(LocalDateTime.now().toString());
//        try {
//            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @MessageMapping("/sendMessage")
//    @SendTo("/chat/{}")
    public Message broadcastGroupMessage(@Payload Message message) {
        log.info("연결 테스트" + message.toString());
//        message.setTimestamp(LocalDateTime.now());
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
//            simpMessagingTemplate.convertAndSend("/chat" + roomIdx, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @GetMapping("/room")
    public ResponseEntity<?> FindRoom(@RequestParam String userIdx){
        UserInfoDto userInfoDto = adminClient.userInfo(userIdx);
        try {
            Room room = roomService.findRoom(userInfoDto.getLocalSchool());
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

    @GetMapping("/message")
    public ResponseEntity<?> getMessage(@RequestParam String roomIdx) {
        List<MessageDto> result = new ArrayList<>();
        try {
            result = chatService.FindPeopleByRoomId(roomIdx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getAll() {
        List<Room> rooms = roomService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }
}