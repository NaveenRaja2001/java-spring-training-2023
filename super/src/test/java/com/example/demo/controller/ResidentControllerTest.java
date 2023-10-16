package com.example.demo.controller;

import com.example.demo.service.ResidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResidentControllerTest {
@InjectMocks
ResidentController residentController;

@Mock
    ResidentService residentService;

    @Test
    void bookHelper() {
        BookingResquest bookingResquest=new BookingResquest();
        bookingResquest.setHelperId(22);
        bookingResquest.setDate("2018-12-22");
        bookingResquest.setUserId(1);
        bookingResquest.setTimeSlotId(1);

        BookingResponse bookingResponse =new BookingResponse();
        bookingResponse.setUserId(1);
        bookingResponse.setHelperId(22);
        bookingResponse.setDate("2018-12-22");

        TimeSlot timeSlot=new TimeSlot();
        timeSlot.setId(1);
        timeSlot.setStarttime("14.00");
        timeSlot.setEndtime("15.00");
        bookingResponse.setTimeslot(List.of(timeSlot));

when(residentService.bookHelper(bookingResquest)).thenReturn(bookingResponse);
assertEquals(residentService.bookHelper(bookingResquest),bookingResponse);
    }

    @Test
    void getAllAvailableHelpers() {
        HelperDetails helperDetails=new HelperDetails();
        helperDetails.setPhonenumber(80732423576L);
        helperDetails.setSkill("plumber");
        helperDetails.setId(1);
        helperDetails.setStatus("active");

        when(residentService.getAllAvailableHelpers("2018-12-22",1)).thenReturn(List.of(helperDetails));
        assertEquals(residentService.getAllAvailableHelpers("2018-12-22",1),List.of(helperDetails));
    }

    @Test
    void getAllTimeslots() {
        TimeSlot response=new TimeSlot();
        response.setId(1);
        response.setStarttime("14.00");
        response.setEndtime("15.00");
        when(residentService.getAllTimeslots()).thenReturn(List.of(response));
        assertEquals(residentService.getAllTimeslots(),List.of(response));
    }
}