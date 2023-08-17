package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.repository.RoomRepository;

import java.util.List;

public interface RoomService {
    Room save(Room room);
    Room find(String name);

    List<Room> findAll();
}
