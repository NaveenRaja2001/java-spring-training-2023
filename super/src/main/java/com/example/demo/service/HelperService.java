package com.example.demo.service;

import org.openapitools.model.BookingResponse;

import java.util.List;

public interface HelperService {
    List<BookingResponse> getAllHelpersBooking(Integer helperId);

}
