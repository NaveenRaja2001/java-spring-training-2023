package com.training.helper.serviceImpl;

import com.training.helper.entities.Appointments;
import com.training.helper.entities.Slots;
import com.training.helper.entities.User;
import com.training.helper.repository.AppointmentRepository;
import com.training.helper.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
/**
 * Test for helper Service
 * @Author Naveen Raja
 */
@ExtendWith(MockitoExtension.class)
class HelperServiceImplTest {

    @InjectMocks
    HelperServiceImpl helperService;

    @Mock
    UserRepository userRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    /**
     * Test method for retrieving all helpers booking
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
        User newUser=new User("Naveen","N","19.10.2001","male","naveen@gmail.com","pass","active");
        newUser.setId(1);
        when(userRepository.findById(2)).thenReturn(Optional.of(newUser));
        Appointments appointments=new Appointments(1,newUser,new Slots(1, LocalTime.parse("12:00"),LocalTime.parse("13:00")),LocalDate.parse("2018-12-21"),2);
        when(appointmentRepository.findByHelperId(2)).thenReturn(List.of(appointments));
            assertEquals(helperService.getAllHelpersBooking(2),List.of(bookingResponse));
    }
}