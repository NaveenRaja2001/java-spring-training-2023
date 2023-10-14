package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Teams;

import java.util.Optional;

public interface TeamService {
    Teams save(Teams teams);


    Optional<Teams> find(int id);
}
