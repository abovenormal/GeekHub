package com.Geekhub.ChatServer.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "chat_room")
@Builder
public class Room implements Serializable {
    @Id
    String _id;
    String localSchool;
}
