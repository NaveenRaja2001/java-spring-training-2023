package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.responseObject.TimeSlotResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MeetingScheduleService{

    TimeSlotResponse addMeeting(TimeSlot timeSlot, int employeeId, String roomName, Optional<Integer> teamid) throws Exception;

    boolean roomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime endTime);

    TimeSlotResponse createNewTeam(List<Integer> employees, TimeSlot timeSlot, int employeeId, String roomName) throws Exception;

    TimeSlotResponse deleteMeeting(int id) throws Exception;

    String updateMeeting(TimeSlot timeSlot, int id);

    String updateMeetingDescription(String description, int timeslotId);

    String updateMeetingAddEmployee(int timeslotId, int employeeid);

    String updateMeetingRemoveEmployee(int timeslotId, int employeeid);
}
