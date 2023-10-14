package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.Teams;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.repository.EmployeeRepository;
import com.training.meetingscheduler.repository.RoomRepository;
import com.training.meetingscheduler.repository.TeamsRepository;
import com.training.meetingscheduler.repository.TimeSlotRepository;
import com.training.meetingscheduler.requestobject.CollaborationRequest;
import com.training.meetingscheduler.responseobject.RoomsResponse;
import com.training.meetingscheduler.responseobject.TeamsResponse;
import com.training.meetingscheduler.responseobject.TimeSlotResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingScheduleServiceTest {
    @InjectMocks
    MeetingScheduleServiceImpl meetingScheduleService;

    @Mock
    RoomRepository roomRepository;
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    TeamsRepository teamsRepository;

    @Mock
    TimeSlotRepository timeSlotRepository;

    @Test
    void addMeeting(){
        TimeSlotResponse expectedResponse=new TimeSlotResponse(2,"hai from team",LocalDate.parse("2023-08-15"), LocalTime.parse("01:10:00"), LocalTime.parse("02:45:00"));
        RoomsResponse roomsResponse=new RoomsResponse(0,"Chennai",3);
        TeamsResponse teamsResponse=new TeamsResponse(0,6,"Snow");
        expectedResponse.setTeams(List.of(teamsResponse));
        expectedResponse.setRooms(List.of(roomsResponse));
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Room r2 = new Room("Chennai", 3);

        when(roomRepository.findByroomName("Chennai")).thenReturn(r2);
        when(employeeRepository.findById(7)).thenReturn(Optional.of(e7));
        Teams t4 = new Teams("Snow", 6);

        when(teamsRepository.findById(4)).thenReturn(Optional.of(t4));
        when(timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(r2, LocalDate.parse("2023-08-15"), LocalTime.parse("02:45:00"), LocalTime.parse("01:10:00"))).thenReturn(new ArrayList<TimeSlot>());
        TimeSlot theTimeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("01:10:00"), LocalTime.parse("02:45:00"), "hai from team");
        theTimeSlot.setTimeSlotId(2);
        TimeSlotResponse response = meetingScheduleService.addMeeting(theTimeSlot, 7, "Chennai", Optional.of(4));

        assertEquals(response,expectedResponse);

    }

    @Test
    void createNewTeam(){
        CollaborationRequest collaborationRequest=new CollaborationRequest(0,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"),List.of(1, 2, 6, 5, 9));
        Employee e1 = new Employee("Naveen", "N", "nav@gmail.com");
        Employee e2 = new Employee("hari", "S", "hari@gmail.com");
        Employee e5 = new Employee("palani", "P", "palani@gmail.com");
        Employee e6 = new Employee("kavya", "K", "kavya@gmail.com");
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Room r6 = new Room("London", 5);
        when(roomRepository.findByroomName("London")).thenReturn(r6);
        when(employeeRepository.findById(6)).thenReturn(Optional.of(e6));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(e1));
        when(employeeRepository.findById(2)).thenReturn(Optional.of(e2));
        when(employeeRepository.findById(5)).thenReturn(Optional.of(e5));
        when(employeeRepository.findById(9)).thenReturn(Optional.of(e9));
        when(timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(r6, LocalDate.parse("2023-08-15"), LocalTime.parse("23:46:00"), LocalTime.parse("22:15:45"))).thenReturn(new ArrayList<TimeSlot>());
        TimeSlot theTimeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        TimeSlotResponse response = meetingScheduleService.addMeeting(collaborationRequest, 6, "London");
TimeSlotResponse expectedResponse=new TimeSlotResponse(0,"hai apart from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));

        RoomsResponse roomsResponse=new RoomsResponse(0,"London",5);
        TeamsResponse teamsResponse=new TeamsResponse(0,4,"Temporary Team");
        expectedResponse.setTeams(List.of(teamsResponse));
        expectedResponse.setRooms(List.of(roomsResponse));
        System.out.print(expectedResponse.getTeams());
        assertEquals(response,expectedResponse);
    }

    @Test
    void deleteMeeting(){
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-09-26"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "");
        timeSlot.setTimeSlotId(1);
        TimeSlotResponse expectResponse=new TimeSlotResponse(timeSlot.getTimeSlotId(),timeSlot.getDescription(),timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime());
        when(timeSlotRepository.findById(1)).thenReturn(Optional.of(timeSlot));
        TimeSlotResponse response = meetingScheduleService.deleteMeeting(1);
        assertEquals(response,expectResponse );
    }


    @Test
    void updateMeeting() {

        Room room = new Room("London", 5);
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "Hai");
        timeSlot.addRoom(room);
        TimeSlotResponse expectResponse=new TimeSlotResponse(timeSlot.getTimeSlotId(),timeSlot.getDescription(),timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime());
        when(timeSlotRepository.findById(2)).thenReturn(Optional.of(timeSlot));
        when(timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(room, LocalDate.parse("2023-08-15"), LocalTime.parse("23:46:00"), LocalTime.parse("22:15:45"))).thenReturn(new ArrayList<TimeSlot>());
        TimeSlotResponse response = meetingScheduleService.updateMeeting(timeSlot, 2);
        assertEquals(response, expectResponse);
    }


//    @Test
//    void updateMeetingDescription() {
//        Room room = new Room("London", 5);
//        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "from team");
//        timeSlot.setTimeSlotId(1);
//        timeSlot.addRoom(room);
//        when(timeSlotRepository.findById(1)).thenReturn(timeSlot);
//        String response = meetingScheduleService.updateMeetingDescription("new Description", 1);
//        assertEquals(response, "Description updated Successfully");
//
//    }



    @Test
    void updateMeetingAddEmployee() {
        TeamsResponse teamsResponse=new TeamsResponse(6,3,"Snow");
        TimeSlotResponse expectedResponse=new TimeSlotResponse(2,"from team",LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        expectedResponse.setTeams(List.of(teamsResponse));
        Teams t4 = new Teams("Snow", 6);
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Employee e8 = new Employee("thendral", "S", "thendral@gmail.com");
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Employee e10 = new Employee("mugilan", "S", "mugilan@gmail.com");
        t4.addEmployee(e7);
        t4.addEmployee(e8);
        t4.addEmployee(e9);
        t4.addEmployee(e10);
        Employee e1 = new Employee("Naveen", "N", "nav@gmail.com");

        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "from team");
        timeSlot.setTimeSlotId(2);
        timeSlot.addTeam(t4);
        when(timeSlotRepository.findById(1)).thenReturn(Optional.of(timeSlot));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(e1));
        TimeSlotResponse response = meetingScheduleService.updateMeetingAddEmployee(1, 1);
        assertEquals(response,expectedResponse );
    }

    @Test
    void updateMeetingRemoveEmployee() {
        TeamsResponse teamsResponse = new TeamsResponse(6, 3, "Snow");
        TimeSlotResponse expectedResponse = new TimeSlotResponse(2, "from team", LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"));
        expectedResponse.setTeams(List.of(teamsResponse));
        Teams t4 = new Teams("Snow", 6);
        Employee e12 = new Employee("d", "S", "hari@gmail.com");
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Employee e8 = new Employee("thendral", "S", "thendral@gmail.com");
        e8.setEmployeeId(8);
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Employee e10 = new Employee("mugilan", "S", "mugilan@gmail.com");
        t4.addEmployee(e7);
        t4.addEmployee(e8);
        t4.addEmployee(e9);

        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "from team");
        timeSlot.setTimeSlotId(2);
        timeSlot.addTeam(t4);
        when(timeSlotRepository.findById(2)).thenReturn(Optional.of(timeSlot));
        TimeSlotResponse response = meetingScheduleService.updateMeetingRemoveEmployee(2, 8);
        assertEquals(response, expectedResponse);
    }


}