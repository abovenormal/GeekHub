package com.Geekhub.ChatServer.dto;


import com.Geekhub.ChatServer.model.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private String userId;
    private String name;
    private String content;
    private String created_at;

}
