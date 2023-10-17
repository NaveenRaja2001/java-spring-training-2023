package com.example.demo.repository;


import com.example.demo.entities.Appointments;
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

    List<Appointments> findByhelperId(Integer userId);

    void deleteAllByhelperId(Integer userId);

    void deleteAllByHelperId(Integer userId);

    List<Appointments> findAllByhelperId(Integer userId);

    List<Appointments> findAllByHelperId(Integer userId);

//    List<Appointments> findAllByUserId(Integer userId);

//    List<Appointments> findAllByUser_id(Integer userId);

    List<Appointments> findAllByResident_id(Integer userId);
//
//    void deleteAllByUser_Id(Integer userId);
//
//    void deleteAllByResidentUser_id(Integer userId);
//
//    void deleteAllByuser_id(Integer userId);
}
