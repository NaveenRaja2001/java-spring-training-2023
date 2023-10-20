package com.training.helper.controller;

import com.training.helper.service.ResidentService;
import org.openapitools.api.ResidentApi;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class that handles Resident-related endpoints
 *
 * @Author Naveen Raja
 */
@RestController
public class ResidentController implements ResidentApi {


    @Autowired
    private ResidentService residentService;

    /**
     * This endpoint is to booked appointment with available helpers
     *
     * @param bookingResquest
     * @return BookingResponse
     */
    @Override
    public ResponseEntity<BookingResponse> bookHelper(BookingResquest bookingResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.bookHelper(bookingResquest));
    }

    /**
     * This endpoint to used to retrieves the available helpers
     *
     * @param date       (required)
     * @param timeslotId (required)
     * @return List of HelperDetails
     */
    @Override
    public ResponseEntity<List<HelperDetails>> getAllAvailableHelpers(String date, Integer timeslotId, String skills) {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.getAllAvailableHelpers(date, timeslotId,skills));
    }
//    @Override
//    public ResponseEntity<List<HelperDetails>> getAllAvailableHelpers(String date, Integer timeslotId) {
//
//    }

    /**
     * This endpoint is used to retrieves all timeslots
     *
     * @return List of TimeSlot
     */
    @Override
    public ResponseEntity<List<TimeSlot>> getAllTimeslots() {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.getAllTimeslots());
    }
}
