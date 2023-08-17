package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room find(String name) {
        return roomRepository.findByroomName(name);
    }
}
