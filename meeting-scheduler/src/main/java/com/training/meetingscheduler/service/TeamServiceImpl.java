package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Teams;
import com.training.meetingscheduler.repository.TeamsRepository;
import com.training.meetingscheduler.serviceinterface.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
     private TeamsRepository teamsRepository;
  @Autowired
    public TeamServiceImpl() {
        this.teamsRepository = teamsRepository;
    }


    @Override
    public Teams save(Teams teams) {
        return teamsRepository.save(teams);
    }

    @Override
    public Optional<Teams> find(int id) {
        return teamsRepository.findById(id);
    }
}
