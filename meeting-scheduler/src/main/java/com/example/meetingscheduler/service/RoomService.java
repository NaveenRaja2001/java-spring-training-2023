package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Room;

public interface RoomService {
    Room save(Room room);
    Room find(String name);
}
