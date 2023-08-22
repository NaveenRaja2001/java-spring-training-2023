package com.example.meetingscheduler.controller;

import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.service.MeetingScheduleService;
import com.example.meetingscheduler.service.MeetingScheduleServiceImpl;

import com.example.meetingscheduler.service.MeetingScheduleServiceImpl;
import com.mysql.cj.x.protobuf.Mysqlx;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingScheduleControllerTest {
    @InjectMocks
    MeetingScheduleController meetingScheduleController;

    @Mock
    MeetingScheduleService meetingScheduleService;

    @Test
    void addMeeting() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("01:10:00"), LocalTime.parse("02:45:00"), " from team");
        timeSlot.setTimeSlotId(2);
        when(meetingScheduleService.addMeeting(timeSlot, 7, "Chennai", Optional.of(4))).thenReturn("Meeting Booked Successfully");
        ResponseEntity<String> response = meetingScheduleController.addMeeting(timeSlot, 7, "Chennai", Optional.of(4));
        assertEquals(response.getBody(), "Meeting Booked Successfully");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void createNewTeam() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        when(meetingScheduleService.createNewTeam(List.of(1, 2, 6, 5, 9), timeSlot, 6, "London")).thenReturn("New team is and meeting is scheduled successfully");
        ResponseEntity<String> response = meetingScheduleController.createNewTeam(List.of(1, 2, 6, 5, 9), timeSlot, 6, "London");
        assertEquals(response.getBody(), "New team is and meeting is scheduled successfully");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void deleteMeeting() {
//        TimeSlot timeSlot=new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"),LocalTime.parse("23:46:00"),"");
        when(meetingScheduleService.deleteMeeting(1)).thenReturn("TimeSlot is deleted successfully");
        when(meetingScheduleService.deleteMeeting(2)).thenReturn("Can't delete the meeting at this moment");
        ResponseEntity<String> response = meetingScheduleController.deleteMeeting(1);
        ResponseEntity<String> response2 = meetingScheduleController.deleteMeeting(2);
        assertEquals(response.getBody(), "TimeSlot is deleted successfully");
        assertEquals(response2.getBody(), "Can't delete the meeting at this moment");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

    }

    @Test
    void updateMeeting() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "Hai");
        when(meetingScheduleService.updateMeeting(timeSlot, 2)).thenReturn("");
        ResponseEntity<String> response = meetingScheduleController.updateMeeting(timeSlot, 2);
        assertEquals(response.getBody(), "");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void updateMeetingDescription() {
        when(meetingScheduleService.updateMeetingDescription("new description", 1)).thenReturn("Description updated Successfully");
        ResponseEntity<String> response = meetingScheduleController.updateMeetingDescription("new description", 1);
        assertEquals(response.getBody(), "Description updated Successfully");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void updateMeetingAddEmployee() {
        when(meetingScheduleService.updateMeetingAddEmployee(1, 2)).thenReturn("Employee has been added to the new meeting");
        ResponseEntity<String> response = meetingScheduleController.updateMeetingAddEmployee(1, 2);
        assertEquals(response.getBody(), "Employee has been added to the new meeting");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void updateMeetingRemoveEmployee() {
        when(meetingScheduleService.updateMeetingRemoveEmployee(1, 2)).thenReturn("Employee has been removed to the new meeting");
        ResponseEntity<String> response = meetingScheduleController.updateMeetingRemoveEmployee(1, 2);
        assertEquals(response.getBody(), "Employee has been removed to the new meeting");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}