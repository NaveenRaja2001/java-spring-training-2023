package com.training.helper.repository;


import com.training.helper.entities.Slots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slots,Integer> {
}
