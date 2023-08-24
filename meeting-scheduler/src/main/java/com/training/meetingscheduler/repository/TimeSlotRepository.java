package com.training.meetingscheduler.repository;

import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.projection.TimeSlotView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot,Integer> {
    List<TimeSlotView> findTimeSlotByDate(LocalDate date);

    List<TimeSlot> findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Room rooms, LocalDate date, LocalTime startTime, LocalTime endTime);
 TimeSlot findById(int id);

// List<TimeSlot> findByDateAndStartTimeGreaterThanEqualOr

    void deleteById(int id);
}
