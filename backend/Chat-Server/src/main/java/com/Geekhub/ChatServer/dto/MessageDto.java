package com.Geekhub.ChatServer.dto;


import com.Geekhub.ChatServer.model.Message;
import lombok.Data;

@Data
public class MessageDto {
    private String sender;
    private String roomId;
    private String content;
    private String timestamp;

    public Message toEntity(){
        Message message = new Message();
        message.setRoomId(roomId);
        message.setRoomId(sender);
        message.setContent(content);
        message.setTimestamp(timestamp);
        return message;
    }
}
