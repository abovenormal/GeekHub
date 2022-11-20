package com.Geekhub.ChatServer.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Document(collection = "chat_log")
public class Message implements Serializable {
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    private String roomId;

    public Message() {
    }

    public Message(String sender, String content,String roomId) {
        this.sender = sender;
        this.content = content;
        this.roomId = roomId;
    }

}