package com.Geekhub.ChatServer.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Document(collection = "chat_log")
@Builder
public class Message implements Serializable {
    private String sender;
    private String content;
    private String timestamp;

    private String roomId;

    public Message() {
    }

    public Message(String sender, String content,String roomId) {
        this.sender = sender;
        this.content = content;
        this.roomId = roomId;
    }

}