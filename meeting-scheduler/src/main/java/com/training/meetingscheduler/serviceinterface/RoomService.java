package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Room;

import java.util.List;

public interface RoomService {
    Room save(Room room);
    Room find(String name);

    List<Room> findAll();
}
