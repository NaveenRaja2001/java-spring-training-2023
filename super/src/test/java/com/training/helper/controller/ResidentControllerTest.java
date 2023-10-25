package com.training.helper.controller;

import com.training.helper.service.ResidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test for Resident Controller
 * @Author Naveen Raja
 */
@ExtendWith(MockitoExtension.class)
class ResidentControllerTest {
    @InjectMocks
    ResidentController residentController;

    @Mock
    ResidentService residentService;

    /**
     * Test method for booking appointment with helper
     */
    @Test
    void bookHelper() {
        BookingResquest bookingResquest = new BookingResquest();
        bookingResquest.setHelperId(22);
        bookingResquest.setDate("2018-12-22");
        bookingResquest.setUserId(1);
        bookingResquest.setTimeSlotId(1);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setUserId(1);
        bookingResponse.setHelperId(22);
        bookingResponse.setDate("2018-12-22");

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(1);
        timeSlot.setStartTime("14.00");
        timeSlot.setEndTime("15.00");
        bookingResponse.setTimeslot(List.of(timeSlot));

        when(residentService.bookHelper(bookingResquest)).thenReturn(bookingResponse);
        assertEquals(residentService.bookHelper(bookingResquest), bookingResponse);

        ResponseEntity<BookingResponse> responseEntity = residentController.bookHelper(bookingResquest);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(bookingResponse);
    }

    /**
     * Tesxt method for retrieving all available helpers
     */
    @Test
    void getAllAvailableHelpers() {
        HelperDetails helperDetails = new HelperDetails();
        helperDetails.setPhoneNumber(80732423576L);
        helperDetails.setSkill("plumber");
        helperDetails.setId(1);
        helperDetails.setStatus("active");

        when(residentService.getAllAvailableHelpers("2018-12-22", 1,null)).thenReturn(List.of(helperDetails));
        assertEquals(residentService.getAllAvailableHelpers("2018-12-22", 1,null), List.of(helperDetails));

        ResponseEntity<List<HelperDetails>> responseEntity = residentController.getAllAvailableHelpers("2018-12-22", 1,null);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(helperDetails));
    }

//    /**
//     * Test for retrieving all timeslots
//     */
//    @Test
//    void getAllTimeslots() {
//        TimeSlot response = new TimeSlot();
//        response.setId(1);
//        response.setStarttime("14.00");
//        response.setEndtime("15.00");
//        when(residentService.getAllTimeslots()).thenReturn(List.of(response));
//        assertEquals(residentService.getAllTimeslots(), List.of(response));
//
//        ResponseEntity<List<TimeSlot>> responseEntity = residentController.getAllTimeslots();
//        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
//        assertThat(responseEntity.getBody()).isEqualTo(List.of(response));
//    }

    /**
     * Test for retrieving all resident booking
     */
    @Test
    void getAllResidentBooking() {
        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setHelperId(2);
        bookingResponse.setDate("2018-12-21");
        bookingResponse.setUserId(1);

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime("12:00");
        timeSlot.setEndTime("13:00");
        timeSlot.setId(1);
        bookingResponse.setTimeslot(List.of(timeSlot));
        when(residentService.getAllResidentBooking(2)).thenReturn(List.of(bookingResponse));
        assertEquals(residentService.getAllResidentBooking(2),List.of(bookingResponse));

        ResponseEntity<List<BookingResponse>> responseEntity = residentController.getAllResidentBooking(2);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(bookingResponse));
    }
    /**
     * Test for retrieving all timeslots with pagination
     */
    @Test
    void getAllTimeslotsWithPagination() {
        TimeSlot response = new TimeSlot();
        response.setId(1);
        response.setStartTime("14.00");
        response.setEndTime("15.00");
        when(residentService.getAllTimeslotsWith(0,1)).thenReturn(List.of(response));
        assertEquals(residentService.getAllTimeslotsWith(0,1), List.of(response));

        ResponseEntity<List<TimeSlot>> responseEntity = residentController.getAllTimeslotsWithPagination(0,1);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(response));
    }
}