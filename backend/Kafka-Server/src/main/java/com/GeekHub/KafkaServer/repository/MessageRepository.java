package com.GeekHub.KafkaServer.repository;

import com.GeekHub.KafkaServer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {

}