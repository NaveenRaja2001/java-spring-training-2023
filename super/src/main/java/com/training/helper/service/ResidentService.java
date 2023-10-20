package com.training.helper.service;

import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;

import java.util.List;

public interface ResidentService {
    List<TimeSlot> getAllTimeslots();

    List<HelperDetails> getAllAvailableHelpers(String date,Integer timeslotId,String skills);

    BookingResponse bookHelper(BookingResquest bookingResquest);

    List<BookingResponse> getAllResidentBooking(Integer residentId);
}
