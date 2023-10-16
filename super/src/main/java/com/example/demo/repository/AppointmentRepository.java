package com.example.demo.repository;


import com.example.demo.entities.Appointments;
import com.example.demo.projection.AppointmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments,Integer> {

    @Query(value="SELECT a.helper_id FROM appointments a join slots s ON a.slots_id =s.id WHERE a.local_date = :date  and s.id = :timeslotId", nativeQuery=true)
    List<Integer> findAllAvailableHelper(LocalDate date, Integer timeslotId);



    List<Appointments> findHelperIdByLocalDateAndSlots_id(LocalDate parse, Integer timeslotId);

    List<Appointments> findByHelperId(Integer helperId);
}
