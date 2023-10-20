package com.training.helper.repository;

import com.training.helper.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for role entity
 *
 * @Author Naveen Raja
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles findByName(String name);
}
