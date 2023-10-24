package com.training.helper.repository;


import com.training.helper.entities.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for Appointment entity
 *
 * @Author Naveen Raja
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Integer> {

    @Query(value = "SELECT a.helper_id FROM appointments a join slots s ON a.slots_id =s.id WHERE a.local_date = :date  and s.id = :timeslotId", nativeQuery = true)
    List<Integer> findAllAvailableHelper(LocalDate date, Integer timeslotId);

    List<Appointments> findHelperIdByLocalDateAndSlots_id(LocalDate parse, Integer timeslotId);

    List<Appointments> findByHelperId(Integer helperId);

    List<Appointments> findAllByHelperId(Integer userId);

    List<Appointments> findAllByResident_id(Integer userId);

}
