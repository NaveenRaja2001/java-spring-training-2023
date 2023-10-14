package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.Teams;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.projection.TimeSlotView;
import com.training.meetingscheduler.repository.RoomRepository;
import com.training.meetingscheduler.repository.TeamsRepository;
import com.training.meetingscheduler.repository.TimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAvailabliltyServiceTest {
    @InjectMocks
    FindAvailabilityServiceImpl findAvailabilityService;

    @Mock
    TimeSlotRepository timeSlotRepository;
    @Mock
    TeamsRepository teamsRepository;

    @Mock
    RoomRepository roomRepository;
    @Test
    void availableRoomsBasedOnDateAndTime() {
        Room r2 = new Room("Chennai", 3);
        TimeSlot reqtimeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        reqtimeSlot.addRoom(r2);
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:35:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        when(timeSlotRepository.findTimeSlotByDate(LocalDate.parse("2023-08-15"))).thenReturn(List.of(reqtimeSlot));
        Room r1 = new Room("TrainingRoom", 12);
        r2.addTimeSlot(reqtimeSlot);
        Room r3 = new Room("Austin", 6);
        Room r4 = new Room("Sydney", 17);
        Room r5 = new Room("Tanjore", 40);
        Room r6 = new Room("London", 5);
        when(roomRepository.findAll()).thenReturn(List.of(r1,r2,r3));
        Map<String, Integer> expected=findAvailabilityService.availableRoomsBasedOnDateAndTime(timeSlot,Optional.empty());
assertEquals(expected,List.of());
    }


    @Test
    void availableEmployeesBasedOnDateAndTime() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        Teams team = new Teams("Snow", 6);
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Employee e8 = new Employee("thendral", "S", "thendral@gmail.com");
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Employee e10 = new Employee("mugilan", "S", "mugilan@gmail.com");
        team.addEmployee(e7);
        e7.addTeam(team);
        team.addEmployee(e8);
        e8.addTeam(team);
        team.addEmployee(e9);
        e9.addTeam(team);
        team.addEmployee(e10);
        e10.addTeam(team);
        team.addTimeSlot(timeSlot);
        timeSlot.setTimeSlotId(2);
        timeSlot.addTeam(team);
        when(teamsRepository.findById(4)).thenReturn(Optional.of(team));
        Map<Integer, Boolean> response = findAvailabilityService.availableEmployeesBasedOnDateAndTime(timeSlot, 4);
        assertEquals(response, Map.of(0, true));

    }


}