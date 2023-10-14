package com.training.meetingscheduler.controller;

import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.service.FindAvailabilityServiceImpl;
import com.training.meetingscheduler.serviceinterface.TimeSlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FindAvailabilityControllerTest {
    @InjectMocks
    FindAvailabilityController findAvailabilityController;

    @Mock
    FindAvailabilityServiceImpl findAvailabilityService;
    @Mock
    private TimeSlotService timeSlotService;

    @Test
    void availableRoomsBasedOnDateAndTime() {

        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        when(findAvailabilityService.availableRoomsBasedOnDateAndTime(timeSlot, Optional.of(4))).thenReturn(Map.of("Chennai", 4));
        ResponseEntity<Map<String, Integer>> response = findAvailabilityController.availableRoomsBasedOnDateAndTime(timeSlot, Optional.of(4));
        assertEquals(response.getBody(), Map.of("Chennai", 4));
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

    }

    @Test
    void availableEmployeesBasedOnDateAndTime() {
        TimeSlot timeSlot = new TimeSlot(LocalDate.parse("2023-08-15"), LocalTime.parse("22:15:45"), LocalTime.parse("23:46:00"), "hai apart from team");
        when(findAvailabilityService.availableEmployeesBasedOnDateAndTime(timeSlot, 4)).thenReturn(Map.of(1, true));
        ResponseEntity<Map<Integer, Boolean>> response = findAvailabilityController.availableEmployeesBasedOnDateAndTime(timeSlot, 4);
        assertEquals(response.getBody(), Map.of(1, true));
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}