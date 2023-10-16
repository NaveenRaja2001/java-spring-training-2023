package com.example.demo.controller;

import com.example.demo.service.ResidentService;
import jakarta.persistence.Id;
import org.openapitools.api.ResidentApi;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
public class ResidentController implements ResidentApi {

    @Autowired
    ResidentService residentService;


    @Override
    public ResponseEntity<BookingResponse> bookHelper(BookingResquest bookingResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.bookHelper(bookingResquest));
    }

    @Override
    public ResponseEntity<List<HelperDetails>> getAllAvailableHelpers(String date, Integer timeslotId) {
       return ResponseEntity.status(HttpStatus.CREATED).body(residentService.getAllAvailableHelpers(date,timeslotId));
    }

    @Override
    public ResponseEntity<List<TimeSlot>> getAllTimeslots() {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.getAllTimeslots());
    }
}
