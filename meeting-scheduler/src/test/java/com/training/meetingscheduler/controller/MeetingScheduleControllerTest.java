package com.training.meetingscheduler.controller;

import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.requestobject.CollaborationRequest;
import com.training.meetingscheduler.responseobject.RoomsResponse;
import com.training.meetingscheduler.responseobject.TeamsResponse;
import com.training.meetingscheduler.responseobject.TimeSlotResponse;
import com.training.meetingscheduler.service.MeetingScheduleServiceImpl;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingScheduleControllerTest {
    @InjectMocks
    MeetingScheduleController meetingScheduleController;

    @Mock
    MeetingScheduleServiceImpl meetingScheduleService;

    @Test
    void addMeeting() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("01:10:00"), LocalTime.parse("02:45:00"), " from team");
        timeSlot.setTimeSlotId(2);
        TimeSlotResponse expectedResponse=new TimeSlotResponse(0,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        RoomsResponse roomsResponse=new RoomsResponse(0,"London",5);
        TeamsResponse teamsResponse=new TeamsResponse(0,4,"Temporary Team");
        expectedResponse.setTeams(List.of(teamsResponse));
        expectedResponse.setRooms(List.of(roomsResponse));
        when(meetingScheduleService.addMeeting(timeSlot, 7, "Chennai", Optional.of(4))).thenReturn(expectedResponse);
        ResponseEntity<TimeSlotResponse> response = meetingScheduleController.addMeeting(timeSlot, 7, "Chennai", Optional.of(4));
        assertEquals(response.getBody(), expectedResponse);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }


    @Test
    void createNewTeam(){
        CollaborationRequest collaborationRequest=new CollaborationRequest(0,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"),List.of(1, 2, 6, 5, 9));
        TimeSlotResponse expectedResponse=new TimeSlotResponse(0,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        RoomsResponse roomsResponse=new RoomsResponse(0,"London",5);
        TeamsResponse teamsResponse=new TeamsResponse(0,4,"Temporary Team");
        expectedResponse.setTeams(List.of(teamsResponse));
        expectedResponse.setRooms(List.of(roomsResponse));
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        when(meetingScheduleService.addMeeting(collaborationRequest, 6, "London")).thenReturn(expectedResponse);
        ResponseEntity<TimeSlotResponse> response = meetingScheduleController.addMeeting(collaborationRequest, 6, "London");
        assertEquals(response.getBody(), expectedResponse);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void deleteMeeting(){
//        TimeSlot timeSlot=new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"),LocalTime.parse("23:46:00"),"");
        TimeSlotResponse expectedResponse=new TimeSlotResponse(2,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        when(meetingScheduleService.deleteMeeting(2)).thenReturn(expectedResponse);
        ResponseEntity<TimeSlotResponse> response = meetingScheduleController.deleteMeeting(2);
        assertEquals(response.getBody(),expectedResponse);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

    }

    @Test
    void updateMeeting() {
        TimeSlotResponse expectedResponse=new TimeSlotResponse(2,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "Hai");
        when(meetingScheduleService.updateMeeting(timeSlot, 2)).thenReturn(expectedResponse);
        ResponseEntity<TimeSlotResponse> response = meetingScheduleController.updateMeeting(timeSlot, 2);
        assertEquals(response.getBody(), expectedResponse);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }


//    @Test
//    void updateMeetingDescription() {
//        when(meetingScheduleService.updateMeetingDescription("new description", 1)).thenReturn("Description updated Successfully");
//        ResponseEntity<String> response = meetingScheduleController.updateMeetingDescription("new description", 1);
//        assertEquals(response.getBody(), "Description updated Successfully");
//        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
//    }



    @Test
    void updateMeetingAddEmployee() {
        TeamsResponse teamsResponse=new TeamsResponse(6,3,"edited");
        TimeSlotResponse expectedResponse=new TimeSlotResponse(2,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        expectedResponse.setTeams(List.of(teamsResponse));
        when(meetingScheduleService.updateMeetingAddEmployee(1, 2)).thenReturn(expectedResponse);
        ResponseEntity<TimeSlotResponse> response = meetingScheduleController.updateMeetingAddEmployee(1, 2);
        assertEquals(response.getBody(), expectedResponse);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void updateMeetingRemoveEmployee() {
        TeamsResponse teamsResponse=new TeamsResponse(6,3,"edited");
        TimeSlotResponse expectedResponse=new TimeSlotResponse(2,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        expectedResponse.setTeams(List.of(teamsResponse));
        when(meetingScheduleService.updateMeetingRemoveEmployee(1, 2)).thenReturn(expectedResponse);
        ResponseEntity<TimeSlotResponse> response = meetingScheduleController.updateMeetingRemoveEmployee(1, 2);
        assertEquals(response.getBody(), expectedResponse);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}