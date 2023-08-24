package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.projection.TimeSlotView;
import com.training.meetingscheduler.repository.TimeSlotRepository;
import com.training.meetingscheduler.serviceinterface.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    @Autowired
    TimeSlotRepository timeSlotRepository;
    public List<TimeSlotView> findTimeSlotIdByDate(LocalDate date) {
//        List<TimeSlot> timeSlots=timeSlotRepository.findAllByDate(date);
//        List<List<Room>> rooms=null;
//        for(TimeSlot timeSlot:timeSlots){
//           rooms.add(timeSlot.getRooms());
//        }
//        System.out.print(rooms);
        return timeSlotRepository.findTimeSlotByDate(date);
    }

    @Override
    public TimeSlot save(TimeSlot timeSlot) {
        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public void delete(int id) {

        timeSlotRepository.deleteById(id);
    }

    @Override
    public TimeSlot find(int id) {
        return timeSlotRepository.findById(id);
    }

    @Override
    public List<TimeSlot> findRoomAvailableBasedOnTime(Room room, LocalDate date, LocalTime endTime, LocalTime startTime) {
        return timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(room,date,endTime,startTime);
    }
}
