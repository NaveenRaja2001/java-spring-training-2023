package com.example.demo.serviceImpl;

import com.example.demo.constants.ErrorConstants;
import com.example.demo.constants.SuccessConstants;
import com.example.demo.entities.Appointments;
import com.example.demo.entities.User;
import com.example.demo.exception.HelperAppException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.HelperService;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles Helper-related endpoints
 *
 * @Author Naveen Raja
 */
@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * This method return the list of helper appointments
     * @param helperId
     * @return List of Booking
     */
    @Override
    public List<BookingResponse> getAllHelpersBooking(Integer helperId) {
        List<BookingResponse> helperBookingResponse = null;
        try{
            User helper=userRepository.findById(helperId).orElseThrow(()->new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if(helper.getStatus().equals(SuccessConstants.STATUS_REQUESTED)){
                throw new HelperAppException(ErrorConstants.INACTIVE_HELPER);
            }
            List<Appointments> appointments=appointmentRepository.findByHelperId(helperId);
            if(appointments.isEmpty()){
                throw new HelperAppException(ErrorConstants.NO_APPOINTMENTS_EXISTS_ERROR);
            }
            helperBookingResponse = appointments.stream()
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
        }catch (HelperAppException e){
            throw new HelperAppException(e.getMessage());
        }
        return helperBookingResponse;
    }
}
