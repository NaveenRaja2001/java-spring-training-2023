package com.example.demo.repository;


import com.example.demo.entities.Slots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slots,Integer> {
}
