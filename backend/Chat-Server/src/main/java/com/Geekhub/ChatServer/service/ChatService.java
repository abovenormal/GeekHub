package com.Geekhub.ChatServer.service;

import com.Geekhub.ChatServer.client.AdminClient;
import com.Geekhub.ChatServer.dto.MessageDto;
import com.Geekhub.ChatServer.dto.UserInfoDto;
import com.Geekhub.ChatServer.model.Message;
import com.Geekhub.ChatServer.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final AdminClient adminClient;

    public List<MessageDto> FindPeopleByRoomId(String roomId){

        List<MessageDto> result = new ArrayList<>();
        List<Message> members = messageRepository.findMessagesByRoomIdOrderByTimestamp(roomId).orElse(null);
        for(Message s: members){
            UserInfoDto userInfoDto = adminClient.userInfo(s.getSender());
            MessageDto messageDto = new MessageDto();
            messageDto.setCreated_at(s.getTimestamp());
            messageDto.setUserId(s.getSender());
            messageDto.setContent(s.getContent());
            messageDto.setName(userInfoDto.getName());
            result.add(messageDto);
        }

        return result;
    }


}
