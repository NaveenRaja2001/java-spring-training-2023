package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.repository.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService{
     private TeamsRepository teamsRepository;
  @Autowired
    public TeamServiceImpl(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }


    @Override
    public Teams save(Teams teams) {
        return teamsRepository.save(teams);
    }

    @Override
    public Teams find(int id) {
        return teamsRepository.findById(id);
    }
}
