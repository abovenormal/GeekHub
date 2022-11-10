package com.Geekhub.ChatServer.service;

import com.Geekhub.ChatServer.model.Message;
import com.Geekhub.ChatServer.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;

    public List<Message> FindPeopleByRoomId(String roomId){

        log.info("service hi");
        List<Message> members = messageRepository.findMessageByRoomId(roomId).orElse(null);
        System.out.println(roomId);
        System.out.println(members.size());
        for(Message s: members){
            System.out.println(s);
        }

        return members;
    }


}
