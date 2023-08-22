package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.repository.EmployeeRepository;
import com.example.meetingscheduler.repository.RoomRepository;
import com.example.meetingscheduler.repository.TeamsRepository;
import com.example.meetingscheduler.repository.TimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    MeetingScheduleService meetingScheduleService;

    @Mock
    RoomRepository roomRepository;
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    TeamsRepository teamsRepository;

    @Mock
    TimeSlotRepository timeSlotRepository;

    @Test
    void addMeeting() {
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Room r2 = new Room("Chennai", 3);

        when(roomRepository.findByroomName("Chennai")).thenReturn(r2);
        when(employeeRepository.findById(7)).thenReturn(e7);
        Teams t4 = new Teams("Snow", 6);

        when(teamsRepository.findById(4)).thenReturn(t4);
        when(timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(r2, LocalDate.parse("2023-08-15"), LocalTime.parse("02:45:00"), LocalTime.parse("01:10:00"))).thenReturn(new ArrayList<TimeSlot>());
        TimeSlot theTimeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("01:10:00"), LocalTime.parse("02:45:00"), "hai from team");
        theTimeSlot.setTimeSlotId(2);
        String response = meetingScheduleService.addMeeting(theTimeSlot, 7, "Chennai", Optional.of(4));

        assertEquals(response, "Meeting Booked Successfully");

    }

    @Test
    void createNewTeam() {
        Employee e1 = new Employee("Naveen", "N", "nav@gmail.com");
        Employee e2 = new Employee("hari", "S", "hari@gmail.com");
        Employee e5 = new Employee("palani", "P", "palani@gmail.com");
        Employee e6 = new Employee("kavya", "K", "kavya@gmail.com");
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Room r6 = new Room("London", 5);
        when(roomRepository.findByroomName("London")).thenReturn(r6);
        when(employeeRepository.findById(6)).thenReturn(e6);
        when(employeeRepository.findById(1)).thenReturn(e1);
        when(employeeRepository.findById(2)).thenReturn(e2);
        when(employeeRepository.findById(5)).thenReturn(e5);
        when(employeeRepository.findById(9)).thenReturn(e9);
        when(timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(r6, LocalDate.parse("2023-08-15"), LocalTime.parse("23:46:00"), LocalTime.parse("22:15:45"))).thenReturn(new ArrayList<TimeSlot>());
        TimeSlot theTimeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        String response = meetingScheduleService.createNewTeam(List.of(1, 2, 5, 9), theTimeSlot, 6, "London");

        assertEquals(response, "New team is and meeting is scheduled successfully");
    }

    @Test
    void deleteMeeting() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "");
        LocalDate localDate = LocalDate.parse("2023-08-15");
        LocalTime startTime = LocalTime.parse("10:15:45");
        LocalTime lastTime = LocalTime.parse("12:15:00");
        when(timeSlotRepository.findById(1)).thenReturn(timeSlot);
        String response = meetingScheduleService.deleteMeeting(1);
        assertEquals(response, "TimeSlot is deleted successfully");
    }

    @Test
    void updateMeeting() {
        Room room = new Room("London", 5);
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "Hai");
        timeSlot.addRoom(room);
        when(timeSlotRepository.findById(2)).thenReturn(timeSlot);
        when(timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(room, LocalDate.parse("2023-08-15"), LocalTime.parse("23:46:00"), LocalTime.parse("22:15:45"))).thenReturn(new ArrayList<TimeSlot>());
        String response = meetingScheduleService.updateMeeting(timeSlot, 2);
        assertEquals(response, "meeting timing is updated");
    }


    @Test
    void updateMeetingDescription() {
        Room room = new Room("London", 5);
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "from team");
        timeSlot.setTimeSlotId(1);
        timeSlot.addRoom(room);
        when(timeSlotRepository.findById(1)).thenReturn(timeSlot);
        String response = meetingScheduleService.updateMeetingDescription("new Description", 1);
        assertEquals(response, "Description updated Successfully");

    }

    @Test
    void updateMeetingAddEmployee() {
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
        timeSlot.addTeam(t4);
        when(timeSlotRepository.findById(1)).thenReturn(timeSlot);
        when(employeeRepository.findById(1)).thenReturn(e1);
        String response = meetingScheduleService.updateMeetingAddEmployee(1, 1);
        assertEquals(response, "Employee has been added to the new meeting");
    }

    @Test
    void updateMeetingRemoveEmployee() {
        Teams t4 = new Teams("Snow", 6);
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Employee e8 = new Employee("thendral", "S", "thendral@gmail.com");
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Employee e10 = new Employee("mugilan", "S", "mugilan@gmail.com");
        t4.addEmployee(e7);
        t4.addEmployee(e8);
        t4.addEmployee(e9);

        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "from team");
        timeSlot.addTeam(t4);
        when(timeSlotRepository.findById(1)).thenReturn(timeSlot);
        String response = meetingScheduleService.updateMeetingRemoveEmployee(1, 1);
        assertEquals(response, "Employee has been removed from the meeting");
    }


}