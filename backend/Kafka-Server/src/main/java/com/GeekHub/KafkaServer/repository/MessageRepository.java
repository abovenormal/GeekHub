package com.GeekHub.KafkaServer.repository;

import com.GeekHub.KafkaServer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MessageRepository extends MongoRepository<Message, String> {

}