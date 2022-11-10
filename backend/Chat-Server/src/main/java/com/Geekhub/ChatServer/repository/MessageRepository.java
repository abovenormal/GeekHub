package com.Geekhub.ChatServer.repository;

import com.Geekhub.ChatServer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    Optional<List<Message>> findMessageByRoomId(String roomId);
}
