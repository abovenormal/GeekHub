package com.Geekhub.ChatServer.service;

import com.Geekhub.ChatServer.dto.RoomDto;
import com.Geekhub.ChatServer.model.Room;
import com.Geekhub.ChatServer.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public void makeRoom(RoomDto roomDto) {
        Room room = Room.builder()
                .localSchool(roomDto.getLocalSchool())
                .build();
        roomRepository.save(room);
    }

    public Room findRoom(String localSchool) {
        Room room = roomRepository.findRoomByLocalSchool(localSchool).orElse(null);
        return room;
    }
}
