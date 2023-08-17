package com.example.meetingscheduler.repository;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams,Integer> {
 Teams findById(int id);
}
