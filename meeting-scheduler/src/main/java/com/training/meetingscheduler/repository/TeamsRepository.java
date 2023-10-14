package com.training.meetingscheduler.repository;

import com.training.meetingscheduler.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams,Integer> {
// Teams findById(int id);

}
