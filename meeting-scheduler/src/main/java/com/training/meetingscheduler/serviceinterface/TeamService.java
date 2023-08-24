package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Teams;

public interface TeamService {
    Teams save(Teams teams);


    Teams find(int id);
}
