package com.Geekhub.ChatServer.repository;

import com.Geekhub.ChatServer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {
    Optional<Message> findall(String roomId);
}
