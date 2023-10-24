package com.training.helper.repository;

import com.training.helper.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for user entity
 *
 * @Author Naveen Raja
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByStatus(String status);
}
