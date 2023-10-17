package com.example.demo.controller;

import com.example.demo.service.HelperService;
import org.openapitools.api.HelperApi;
import org.openapitools.model.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class that handles helpers-related endpoints
 *
 * @Author Naveen Raja
 */
@RestController
public class HelperController implements HelperApi {
    @Autowired
    HelperService helperService;

    /**
     * This endpoint retrieves the appointment booked for helpers
     *
     * @param helperId (required)
     * @return List of BookingResponse
     */
    @Override
    public ResponseEntity<List<BookingResponse>> getAllHelpersBooking(Integer helperId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(helperService.getAllHelpersBooking(helperId));
    }

}
