package com.example.demo.repository;

//import com.training.helper.entities.User;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
}
