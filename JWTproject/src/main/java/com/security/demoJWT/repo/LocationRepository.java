package com.security.demoJWT.repo;

import com.security.demoJWT.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Integer> {

  Optional<Location> findBylocationName(String name);
}
