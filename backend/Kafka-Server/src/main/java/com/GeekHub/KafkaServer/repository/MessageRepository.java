package com.GeekHub.KafkaServer.repository;

import com.GeekHub.KafkaServer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    //@Query(value = "find({'driver':?0},{$top:{sortBy:{'timestamp':-1},output['driver','longitude,'latitude'']}})")
    @Query(value = "{'driver':?0}",sort = "{'timestamp':-1}")
    List<Message> getMessage(String driver);


}