package com.training.meetingscheduler.repository;

import com.training.meetingscheduler.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {
    Room findByroomName(String roomName);
}
