package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.requestobject.CollaborationRequest;
import com.training.meetingscheduler.responseobject.TimeSlotResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MeetingScheduleService{

    TimeSlotResponse addMeeting(TimeSlot timeSlot, int employeeId, String roomName, Optional<Integer> teamid) throws Exception;

    boolean roomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime endTime);

    TimeSlotResponse addMeeting(CollaborationRequest collaborationRequest, int employeeId, String roomName) throws Exception;

    TimeSlotResponse deleteMeeting(int id) throws Exception;

    TimeSlotResponse updateMeeting(TimeSlot timeSlot, int id);

    TimeSlotResponse updateMeetingDescription(TimeSlot theTimeSlot,TimeSlot timeSlot,TimeSlotResponse timeSlotResponse);

    TimeSlotResponse updateMeetingAddEmployee(int timeslotId, int employeeid);

    TimeSlotResponse updateMeetingRemoveEmployee(int timeslotId, int employeeid);
}
