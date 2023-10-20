package com.training.helper.repository;

//import com.training.helper.entities.User;
import com.training.helper.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String username);

    Optional<User>findByEmail(String email);

    @Query(
            value = "SELECT * FROM USER u WHERE u.status = 'requested'",
            nativeQuery = true)
    List<User> getAllRequestedUser();

    Optional<User> findByStatus(String status);
    @Query(
            value = "SELECT * FROM USER u WHERE u.status = 'rejected'",
            nativeQuery = true)
    List<User> getAllRejectedUser();

    List<User> findAllByStatus(String status);
}
