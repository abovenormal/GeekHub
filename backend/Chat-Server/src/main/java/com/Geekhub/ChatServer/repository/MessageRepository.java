package com.Geekhub.ChatServer.repository;

import com.Geekhub.ChatServer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
