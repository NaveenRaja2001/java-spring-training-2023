package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Teams;

public interface TeamService {
    Teams save(Teams teams);


    Teams find(int id);
}
