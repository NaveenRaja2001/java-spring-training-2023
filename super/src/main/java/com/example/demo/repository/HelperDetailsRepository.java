package com.example.demo.repository;

//import com.training.helper.entities.HelperDetails;
import com.example.demo.entities.HelperDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelperDetailsRepository extends JpaRepository<HelperDetails,Integer> {
    List<HelperDetails> findAllByStatusNot(String active);

    List<HelperDetails> findByStatusNot(String string);

    List<HelperDetails> findByUser_idNotIn(List<Integer> asa);
}
