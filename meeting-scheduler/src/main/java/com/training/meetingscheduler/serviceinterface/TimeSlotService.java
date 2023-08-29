package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotService {

    List<TimeSlot> findTimeSlotIdByDate(LocalDate date);
     TimeSlot save(TimeSlot timeSlot);

     void delete(int id);

    Optional<TimeSlot> find(int id);

    List<TimeSlot> findRoomAvailableBasedOnTime(Room room, LocalDate date, LocalTime endTime, LocalTime startTime);
}
