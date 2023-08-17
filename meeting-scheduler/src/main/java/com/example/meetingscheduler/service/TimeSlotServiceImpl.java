package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.projection.TimeSlotView;
import com.example.meetingscheduler.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService{
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
}
