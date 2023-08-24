package com.training.meetingscheduler.projection;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.Teams;

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
