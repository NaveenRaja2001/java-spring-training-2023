package com.example.meetingscheduler.projection;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotView {
     int getTimeSlotId();
     String getDescription();

     LocalDate getDate();

     LocalTime getstartTime();
     LocalTime getendTime();
     List<Room> getRooms();

     List<Teams> getTeams();

     Employee getEmployee();

}
