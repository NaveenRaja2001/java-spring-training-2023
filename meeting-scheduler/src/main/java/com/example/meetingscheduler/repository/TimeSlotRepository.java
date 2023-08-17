package com.example.meetingscheduler.repository;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.projection.TimeSlotView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot,Integer> {
    List<TimeSlotView> findTimeSlotByDate(LocalDate date);

    List<TimeSlot> findByRoomsAndDateAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Room rooms, LocalDate date, LocalTime startTime, LocalTime endTime);
 TimeSlot findById(int id);

    void deleteById(int id);
}