package com.training.helper.repository;


import com.training.helper.entities.Slots;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *  Interface for timeslot entity
 *
 * @Author Naveen Raja
 */
public interface SlotRepository extends JpaRepository<Slots,Integer> {
}
