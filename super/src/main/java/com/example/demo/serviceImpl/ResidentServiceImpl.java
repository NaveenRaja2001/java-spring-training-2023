package com.example.demo.serviceImpl;

import com.example.demo.constants.ErrorConstants;
import com.example.demo.constants.SuccessConstants;
import com.example.demo.entities.Appointments;
import com.example.demo.entities.Slots;
import com.example.demo.entities.User;
import com.example.demo.exception.HelperAppException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.HelperDetailsRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ResidentService;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    SlotRepository slotRepository;

    @Autowired
    HelperDetailsRepository helperDetailsRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<TimeSlot> getAllTimeslots() {
        List<TimeSlot> timeSlots = null;
        try {
            var availableTimeSlots = slotRepository.findAll();
            timeSlots = availableTimeSlots.stream()
                    .map(slots -> {
                        TimeSlot timeSlot = new TimeSlot();
                        timeSlot.setStarttime(slots.getStartTime().toString());
                        timeSlot.setEndtime(slots.getEndTime().toString());
                        timeSlot.setId(slots.getId());
                        return timeSlot;
                    })
                    .collect(Collectors.toList());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return timeSlots;
    }

    @Override
    public List<HelperDetails> getAllAvailableHelpers(String date, Integer timeslotId) {

//List<com.example.demo.entities.HelperDetails> helper=helperDetailsRepository.findByStatusNot("string");
//List<Integer> availableHelperId=appointmentRepository.findAllAvailableHelper(LocalDate.parse(date),timeslotId);
        List<HelperDetails> availableHelper;
        try {
            List<Appointments> bookedHelpers = appointmentRepository.findHelperIdByLocalDateAndSlots_id(LocalDate.parse(date), timeslotId);
            List<Integer> bookedHelpersId = bookedHelpers.stream()
                    .map(Appointments::getHelperId)
                    .collect(Collectors.toList());
            if (bookedHelpersId.isEmpty()) {
                bookedHelpersId.add(0);
            }
            List<com.example.demo.entities.HelperDetails> availableHelperDetails = helperDetailsRepository.findByUser_idNotIn(bookedHelpersId);
            availableHelper = availableHelperDetails.stream()
                    .map(h -> {
                        HelperDetails helperDetails = new HelperDetails();
                        helperDetails.setId(h.getUser().getId());
                        helperDetails.setPhonenumber(h.getPhoneNumber());
                        helperDetails.setSkill(h.getSkill());
                        helperDetails.setStatus(h.getStatus());
                        return helperDetails;
                    })
                    .collect(Collectors.toList());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return availableHelper;
    }

    @Override
    public BookingResponse bookHelper(BookingResquest bookingResquest) {
        BookingResponse bookingResponse;
        try {


            List<Appointments> bookedHelpers = appointmentRepository.findHelperIdByLocalDateAndSlots_id(LocalDate.parse(bookingResquest.getDate()), bookingResquest.getTimeSlotId());
            List<Integer> bookedHelpersId = bookedHelpers.stream()
                    .map(Appointments::getHelperId)
                    .collect(Collectors.toList());
            if (bookedHelpersId.contains(bookingResquest.getHelperId())) {
                throw new HelperAppException(ErrorConstants.HELPER_ALREADY_BOOKED_ERROR);
            }
            User resident = userRepository.findById(bookingResquest.getUserId()).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            User helper = userRepository.findById(bookingResquest.getHelperId()).orElseThrow(() -> new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR));
            if (helper.getStatus().equals(SuccessConstants.STATUS_REQUESTED)) {
                throw new HelperAppException(ErrorConstants.INACTIVE_HELPER);
            }
            Slots timeSlot = slotRepository.findById(bookingResquest.getTimeSlotId()).orElseThrow(() -> new HelperAppException(ErrorConstants.INVALID_TIMESLOT_ERROR));
            Appointments appointments = new Appointments(bookingResquest.getTimeSlotId(), resident, timeSlot, LocalDate.parse(bookingResquest.getDate()), bookingResquest.getHelperId());
            appointmentRepository.save(appointments);

            TimeSlot bookedTimeSlot = new TimeSlot();
            bookedTimeSlot.setId(timeSlot.getId());
            bookedTimeSlot.setStarttime(timeSlot.getStartTime().toString());
            bookedTimeSlot.setEndtime(timeSlot.getEndTime().toString());
            bookingResponse = new BookingResponse();
            bookingResponse.setTimeslot(List.of(bookedTimeSlot));
            bookingResponse.setHelperId(appointments.getHelperId());
            bookingResponse.setUserId(appointments.getResident().getId());
            bookingResponse.setDate(appointments.getLocalDate().toString());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return bookingResponse;
    }


}
