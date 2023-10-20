package com.training.helper.repository;

//import com.training.helper.entities.HelperDetails;
import com.training.helper.entities.HelperDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HelperDetailsRepository extends JpaRepository<HelperDetails,Integer> {
    List<HelperDetails> findAllByStatusNot(String active);

    List<HelperDetails> findByStatusNot(String string);

    List<HelperDetails> findByUser_idNotIn(List<Integer> asa);

    Optional<HelperDetails> findByUser_id(Integer userId);
}
