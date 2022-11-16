package com.Geekhub.ChatServer.service;

import com.Geekhub.ChatServer.client.AdminClient;
import com.Geekhub.ChatServer.dto.MessageDto;
import com.Geekhub.ChatServer.dto.UserInfoDto;
import com.Geekhub.ChatServer.model.Message;
import com.Geekhub.ChatServer.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            String minTime = "";
            int min = s.getTimestamp().getMinute();
            if (min < 10) {
                minTime = "0" + min;
            }else {
                minTime = String.valueOf(min);
            }
            if (Objects.equals(s.getTimestamp().toLocalDate(), LocalDate.now())) {
                UserInfoDto userInfoDto = adminClient.userInfo(s.getSender());
                MessageDto messageDto = new MessageDto();
                messageDto.setCreated_at(s.getTimestamp().getHour() + ":" + minTime);
                messageDto.setUserId(s.getSender());
                messageDto.setContent(s.getContent());
                messageDto.setName(userInfoDto.getName());
                result.add(messageDto);
            }
        }

        return result;
    }


}
