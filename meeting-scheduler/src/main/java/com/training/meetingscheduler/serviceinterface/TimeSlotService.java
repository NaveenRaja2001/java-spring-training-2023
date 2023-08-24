package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.projection.TimeSlotView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotService {

    List<TimeSlotView> findTimeSlotIdByDate(LocalDate date);
     TimeSlot save(TimeSlot timeSlot);

     void delete(int id);

    TimeSlot find(int id);

    List<TimeSlot> findRoomAvailableBasedOnTime(Room room, LocalDate date, LocalTime endTime, LocalTime startTime);
}
