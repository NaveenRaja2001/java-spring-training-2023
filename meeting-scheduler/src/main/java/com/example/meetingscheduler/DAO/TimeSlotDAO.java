package com.example.meetingscheduler.DAO;

import com.example.meetingscheduler.entity.TimeSlot;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
@Repository
public class TimeSlotDAO {
    private EntityManager entityManager;
    @Autowired
    public TimeSlotDAO(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    public TimeSlot findByInstructorId(int id) {
        return entityManager.find(TimeSlot.class,id);
    }
}
