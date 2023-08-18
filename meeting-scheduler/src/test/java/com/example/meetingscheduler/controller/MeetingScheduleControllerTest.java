package com.example.meetingscheduler.controller;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.service.TeamService;
import com.example.meetingscheduler.service.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
//@ExtendWith(MockitoExtension.class)

class MeetingScheduleControllerTest {
    @Autowired
    FindAvailabliltyController findAvailabliltyController;
    @Autowired
TeamService teamService;
    @Test
    void availableRoomsBasedOnDateAndTime() {


    }

    @Test
    void availableEmployeesBasedOnDateAndTime() {


        Room r4 = new Room("Sydney", 17);
        Teams team3 = new Teams("Data", 1);
        team3.setTeamId(3);
        Employee e6 = new Employee("kavya", "K", "kavya@gmail.com");
        e6.setEmployeeId(6);
        team3.addEmployee(e6);
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("02:35:55"), LocalTime.parse("13:36:00"), "regarding UIBC competition");
        timeSlot.setEmployee(e6);
        timeSlot.addRoom(r4);
        timeSlot.addTeam(team3);

        HashMap<Integer, Boolean> expectList=findAvailabliltyController.availableEmployeesBasedOnDateAndTime(timeSlot,3);
        when(teamService.find(3)).thenReturn(team3);


        HashMap<Integer, Boolean> expectedList=new HashMap<>();
        expectedList.put(6,true);
        when(findAvailabliltyController.availableEmployeeBasedOnDateAndTime(timeSlot,e6)).thenReturn(true);
        assertEquals(expectedList,expectList);

    }

}