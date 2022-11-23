package com.Geekhub.ChatServer.repository;

import com.Geekhub.ChatServer.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    Optional<Room> findRoomByLocalSchool(String localSchool);
}
