package com.example.meetingscheduler.responseObject;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotResponse {
   public int getTimeSlotId;
    public  String getDescription;

    LocalDate localDate;
    LocalTime startTime;

    LocalTime endTime;

    public List<Room> Rooms;

    public TimeSlotResponse(int getTimeSlotId, String getDescription, LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        this.getTimeSlotId = getTimeSlotId;
        this.getDescription = getDescription;
        this.localDate = localDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Teams> Teams;

    public Employee Employee;


}
