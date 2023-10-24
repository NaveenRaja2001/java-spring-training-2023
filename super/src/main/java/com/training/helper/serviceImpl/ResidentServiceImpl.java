package com.training.helper.serviceImpl;

import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.ErrorConstants;
import com.training.helper.entities.Appointments;
import com.training.helper.entities.Slots;
import com.training.helper.entities.User;
import com.training.helper.exception.HelperAppException;
import com.training.helper.repository.AppointmentRepository;
import com.training.helper.repository.HelperDetailsRepository;
import com.training.helper.repository.SlotRepository;
import com.training.helper.repository.UserRepository;
import com.training.helper.service.ResidentService;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.BookingResquest;
import org.openapitools.model.HelperDetails;
import org.openapitools.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles Resident-related endpoints
 *
 * @Author Naveen Raja
 */
@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private HelperDetailsRepository helperDetailsRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * This method gives the available timeslots
     *
     * @return List of Timeslot
     */
    @Override
    public List<TimeSlot> getAllTimeslots() {
        List<TimeSlot> timeSlots = null;
        try {
            List<Slots> availableTimeSlots = slotRepository.findAll();
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

    /**
     * This method return the list of available  helpers
     *
     * @param date
     * @param timeslotId
     * @return List of Helper
     */
    @Override
    public List<HelperDetails> getAllAvailableHelpers(String date, Integer timeslotId, String skills) {
        List<HelperDetails> availableHelper;
        List<com.training.helper.entities.HelperDetails> availableHelperBySkills = new ArrayList<>();
        try {
            Slots timeSlot = slotRepository.findById(timeslotId).orElseThrow(() -> new HelperAppException(ErrorConstants.INVALID_TIMESLOT_ERROR));
            List<Appointments> bookedHelpers = appointmentRepository.findHelperIdByLocalDateAndSlots_id(LocalDate.parse(date), timeslotId);
            List<Integer> bookedHelpersId = bookedHelpers.stream()
                    .map(Appointments::getHelperId)
                    .collect(Collectors.toList());
            if (bookedHelpersId.isEmpty()) {
                bookedHelpersId.add(0);
            }
            List<com.training.helper.entities.HelperDetails> availableHelperDetails = helperDetailsRepository.findByUser_idNotIn(bookedHelpersId);

            if (skills != null) {
                availableHelperDetails.removeIf(h -> !skills.toLowerCase().equals(h.getSkill()));
            }
            availableHelper = availableHelperDetails.stream()
                    .filter(h -> h.getUser().getStatus().equals(CommonConstants.STATUS_APPROVED))
                    .map(h -> {
                        HelperDetails helperDetails = new HelperDetails();
                        helperDetails.setId(h.getUser().getId());
                        helperDetails.setPhonenumber(h.getPhoneNumber());
                        helperDetails.setSkill(h.getSkill());
                        helperDetails.setStatus(h.getUser().getStatus());
                        return helperDetails;
                    })
                    .collect(Collectors.toList());
            if (availableHelper.isEmpty()) {
                throw new HelperAppException("No Helper is found");
            }
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return availableHelper;
    }

    /**
     * This method is used to book appointment with helper
     *
     * @param bookingResquest
     * @return BookingResponse
     */
    @Override
    public BookingResponse bookHelper(BookingResquest bookingResquest) {
        BookingResponse bookingResponse;
        try {
            LocalDate todayDate = LocalDate.now().plusDays(1);
            if (todayDate.isAfter(LocalDate.parse(bookingResquest.getDate()))) {
                throw new HelperAppException("Booking can done only after " + todayDate.minusDays(1));
            }
            List<Appointments> bookedHelpers = appointmentRepository.findHelperIdByLocalDateAndSlots_id(LocalDate.parse(bookingResquest.getDate()), bookingResquest.getTimeSlotId());

            List<Integer> bookedHelpersId = bookedHelpers.stream()
                    .map(Appointments::getHelperId)
                    .collect(Collectors.toList());
            if (bookedHelpersId.contains(bookingResquest.getHelperId())) {
                throw new HelperAppException(ErrorConstants.HELPER_ALREADY_BOOKED_ERROR);
            }
            User resident = userRepository.findById(bookingResquest.getUserId()).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            User helper = userRepository.findById(bookingResquest.getHelperId()).orElseThrow(() -> new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR));
            if (helper.getStatus().equals(CommonConstants.STATUS_REQUESTED) || helper.getStatus().equals(CommonConstants.STATUS_REJECTED)) {
                throw new HelperAppException(ErrorConstants.INACTIVE_HELPER);
            }
            Slots timeSlot = slotRepository.findById(bookingResquest.getTimeSlotId()).orElseThrow(() -> new HelperAppException(ErrorConstants.INVALID_TIMESLOT_ERROR));
            Appointments appointments = new Appointments(resident, timeSlot, LocalDate.parse(bookingResquest.getDate()), bookingResquest.getHelperId());
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

    /**
     * The method is retrieves all the resident booking
     *
     * @param residentId
     * @return
     */
    @Override
    public List<BookingResponse> getAllResidentBooking(Integer residentId) {
        List<BookingResponse> residentBookingResponse = null;
        try {
            User resident = userRepository.findById(residentId).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if (resident.getStatus().equals(CommonConstants.STATUS_REQUESTED) || resident.getStatus().equals(CommonConstants.STATUS_REJECTED)) {
                throw new HelperAppException(ErrorConstants.INACTIVE_HELPER);
            }
            List<Appointments> appointments = appointmentRepository.findAllByResident_id(residentId);
            if (appointments.isEmpty()) {
                throw new HelperAppException(ErrorConstants.NO_APPOINTMENTS_EXISTS_ERROR);
            }
            residentBookingResponse = appointments.stream()
                    .map(appointment -> {
                        BookingResponse response = new BookingResponse();
                        response.setHelperId(appointment.getHelperId());
                        response.setDate(appointment.getLocalDate().toString());
                        response.setUserId(appointment.getResident().getId());

                        TimeSlot timeSlot = new TimeSlot();
                        timeSlot.setStarttime(appointment.getSlots().getStartTime().toString());
                        timeSlot.setEndtime(appointment.getSlots().getEndTime().toString());
                        timeSlot.setId(appointment.getSlots().getId());
                        response.setTimeslot(List.of(timeSlot));

                        return response;
                    })
                    .collect(Collectors.toList());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return residentBookingResponse;
    }

    /**
     * This method gives the available timeslots with pagination
     *
     * @param offset
     * @param pageSize
     * @return
     */
    @Override
    public List<TimeSlot> getAllTimeslotsWith(Integer offset, Integer pageSize) {
        List<TimeSlot> timeSlots = null;
        try {
            Pageable pageable = PageRequest.of(offset, pageSize);
            List<Slots> availableTimeSlots = slotRepository.findAll(pageable).toList();
            if (availableTimeSlots.isEmpty()) {
                throw new HelperAppException(CommonConstants.TIMESLOT_NOT_FOUND);
            }
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
}
