package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.projection.TimeSlotView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TimeSlotService {

    List<TimeSlotView> findTimeSlotIdByDate(LocalDate date);
     TimeSlot save(TimeSlot timeSlot);

     void delete(int id);

    TimeSlot find(int id);
}
