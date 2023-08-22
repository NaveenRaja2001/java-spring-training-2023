package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MeetingScheduleService{

    String addMeeting(TimeSlot timeSlot, int employeeId, String roomName, Optional<Integer> teamid);

    boolean roomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime endTime);

    String createNewTeam(List<Integer> employees, TimeSlot timeSlot, int employeeId, String roomName);

    String deleteMeeting(int id);

    String updateMeeting(TimeSlot timeSlot, int id);

    String updateMeetingDescription(String description, int timeslotId);

    String updateMeetingAddEmployee(int timeslotId, int employeeid);

    String updateMeetingRemoveEmployee(int timeslotId, int employeeid);
}
