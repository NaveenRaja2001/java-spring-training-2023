package com.example.demo.controller;

import com.example.demo.service.HelperService;
import com.example.demo.serviceImpl.HelperServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.TimeSlot;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class HelperControllerTest {
@InjectMocks
HelperController helperController;

@Mock
    HelperServiceImpl helperService;

    @Test
    void getAllHelpersBooking() {
        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setHelperId(2);
        bookingResponse.setDate("2018-12-21");
        bookingResponse.setUserId(1);

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStarttime("12:00");
        timeSlot.setEndtime("13:00");
        timeSlot.setId(1);
        bookingResponse.setTimeslot(List.of(timeSlot));
        when(helperService.getAllHelpersBooking(2)).thenReturn(List.of(bookingResponse));
        assertEquals(helperService.getAllHelpersBooking(2),List.of(bookingResponse));

    }
}