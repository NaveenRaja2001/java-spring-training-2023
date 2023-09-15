package com.security.demoJWT.repo;

import com.security.demoJWT.entity.Booked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRepository extends JpaRepository<Booked,Integer> {
}
