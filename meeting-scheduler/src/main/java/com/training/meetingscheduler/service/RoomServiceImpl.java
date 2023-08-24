package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.repository.RoomRepository;
import com.training.meetingscheduler.serviceinterface.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
