package com.training.helper.repository;

//import com.training.helper.entities.Roles;
import com.training.helper.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {

    Roles findByName(String name);
}
