package com.training.helper.controller;

import com.training.helper.serviceImpl.HelperServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.TimeSlot;
import org.openapitools.model.UserRegistrationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test for helper controller endpoints
 *
 * @Author Naveen Raja
 */
@ExtendWith(MockitoExtension.class)
class HelperControllerTest {
@InjectMocks
HelperController helperController;

@Mock
    HelperServiceImpl helperService;

    /**
     * Test method of retrieve all helper booking
     */
    @Test
    void getAllHelpersBooking() {
        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setHelperId(2);
        bookingResponse.setDate("2018-12-21");
        bookingResponse.setUserId(1);

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime("12:00");
        timeSlot.setEndTime("13:00");
        timeSlot.setId(1);
        bookingResponse.setTimeslot(List.of(timeSlot));
        when(helperService.getAllHelpersBooking(2)).thenReturn(List.of(bookingResponse));
        assertEquals(helperService.getAllHelpersBooking(2),List.of(bookingResponse));

        ResponseEntity<List<BookingResponse>> responseEntity = helperController.getAllHelpersBooking(2);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(bookingResponse));
    }
}