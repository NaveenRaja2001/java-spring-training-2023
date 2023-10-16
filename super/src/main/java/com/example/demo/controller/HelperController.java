package com.example.demo.controller;

import com.example.demo.service.HelperService;
import org.openapitools.api.HelperApi;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class HelperController implements HelperApi {
    @Autowired
    HelperService helperService;

    @Override
    public ResponseEntity<List<BookingResponse>> getAllHelpersBooking(Integer helperId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(helperService.getAllHelpersBooking(helperId));
    }

//    @Override
//    public ResponseEntity<HelperBookingResponse> getAllHelpersBooking(Integer helperId) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(helperService.getAllHelpersBooking(helperId));
//    }


//    @Override
//    public ResponseEntity<BookingResponse> getAllHelpersBooking(Integer helperId) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(helperService.getAllHelpersBooking(helperId));
//    }
}
