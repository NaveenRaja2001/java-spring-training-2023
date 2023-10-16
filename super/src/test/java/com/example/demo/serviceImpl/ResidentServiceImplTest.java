package com.example.demo.serviceImpl;

import com.example.demo.entities.Appointments;
import com.example.demo.entities.Slots;
import com.example.demo.entities.User;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.HelperDetailsRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResidentServiceImplTest {
    @InjectMocks
    ResidentServiceImpl residentService;

    @Mock
    SlotRepository slotRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    HelperDetailsRepository helperDetailsRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void getAllTimeslots() {
        TimeSlot response = new TimeSlot();
        response.setId(1);
        response.setStarttime("14:00");
        response.setEndtime("15:00");
        Slots timeslots = new Slots(1, LocalTime.parse("14:00"), LocalTime.parse("15:00"));
        when(slotRepository.findAll()).thenReturn(List.of(timeslots));
        assertEquals(residentService.getAllTimeslots(), List.of(response));

    }

    @Test
    void getAllAvailableHelpers() {
        HelperDetails response = new HelperDetails();
        response.setPhonenumber(80732423576L);
        response.setSkill("plumber");
        response.setId(1);
        response.setStatus("active");

        Appointments appointments = new Appointments(1, new User(), new Slots(1, LocalTime.parse("14:00"), LocalTime.parse("15:00")), LocalDate.parse("2018-12-22"), 22);
        when(appointmentRepository.findHelperIdByLocalDateAndSlots_id(LocalDate.parse("2018-12-22"), 1)).thenReturn(List.of(appointments));
        User newUser = new User("Naveen", "N", "19.10.2001", "male", "naveen@gmail.com", "pass", "approved");
        newUser.setId(1);
        com.example.demo.entities.HelperDetails helperDetails = new com.example.demo.entities.HelperDetails(1, newUser, 80732423576L, "plumber", "active");
        when(helperDetailsRepository.findByUser_idNotIn(List.of(22))).thenReturn(List.of(helperDetails));
        assertEquals(residentService.getAllAvailableHelpers("2018-12-22", 1), List.of(response));
    }

    @Test
    void bookHelper() {
        BookingResquest bookingResquest=new BookingResquest();
        bookingResquest.setHelperId(13);
        bookingResquest.setDate("2018-12-22");
        bookingResquest.setUserId(21);
        bookingResquest.setTimeSlotId(1);

        User newUser = new User("Naveen", "N", "19.10.2001", "male", "naveen@gmail.com", "pass", "approved");
        newUser.setId(1);
        Appointments appointments = new Appointments(1, newUser, new Slots(1, LocalTime.parse("14:00"), LocalTime.parse("15:00")), LocalDate.parse("2018-12-22"), 22);
        when(appointmentRepository.findHelperIdByLocalDateAndSlots_id(LocalDate.parse("2018-12-22"), 1)).thenReturn(List.of(appointments));
        User resident = new User("Sakthi", "S", "19.01.2001", "male", "sakthi@gmail.com", "pass", "approved");
        resident.setId(21);
        User helper = new User("Pavi", "P", "19.01.2001", "female", "pavi@gmail.com", "pass", "approved");
        helper.setId(13);
        when(userRepository.findById(21)).thenReturn(Optional.of(resident));
        when(userRepository.findById(13)).thenReturn(Optional.of(helper));
        Slots timeslots = new Slots(1, LocalTime.parse("14:00"), LocalTime.parse("15:00"));
        when(slotRepository.findById(1)).thenReturn(Optional.of(timeslots));

        BookingResponse bookingResponse =new BookingResponse();
        bookingResponse.setUserId(21);
        bookingResponse.setHelperId(13);
        bookingResponse.setDate("2018-12-22");

        TimeSlot timeSlot=new TimeSlot();
        timeSlot.setId(1);
        timeSlot.setStarttime("14:00");
        timeSlot.setEndtime("15:00");
        bookingResponse.setTimeslot(List.of(timeSlot));
        assertEquals(residentService.bookHelper(bookingResquest),bookingResponse);
    }
}