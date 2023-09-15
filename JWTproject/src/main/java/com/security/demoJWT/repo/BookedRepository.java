package com.security.demoJWT.repo;

import com.security.demoJWT.entity.Booked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedRepository extends JpaRepository<Booked,Integer> {
    List<Booked> findAllByStatus(String Status);
}
