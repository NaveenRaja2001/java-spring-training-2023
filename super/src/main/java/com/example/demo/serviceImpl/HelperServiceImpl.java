package com.example.demo.serviceImpl;

import com.example.demo.entities.Appointments;
import com.example.demo.exception.HelperAppException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.service.HelperService;
import org.openapitools.model.BookingResponse;
import org.openapitools.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<BookingResponse> getAllHelpersBooking(Integer helperId) {
        List<BookingResponse> helperBookingResponse = null;
        try{
            List<Appointments> appointments=appointmentRepository.findByHelperId(helperId);
            if(appointments.isEmpty()){
                throw new HelperAppException("currently there is no appointments for the helper");
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

        }catch (Exception e){
            throw new HelperAppException(e.getMessage());
        }


        return helperBookingResponse;
    }
}
