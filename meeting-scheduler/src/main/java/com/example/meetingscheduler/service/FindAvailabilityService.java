package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.TimeSlot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface FindAvailabilityService{
    public Map<String, Integer> availableRoomsBasedOnDateAndTime(TimeSlot theTimeSlot, Optional<Integer> teamId, Optional<Integer> teamCount);

    public  HashMap<String, Integer> sortByValue(HashMap<String, Integer> array);
    public HashMap<Integer, Boolean> availableEmployeesBasedOnDateAndTime(TimeSlot theTimeSlot, int teamId);
    public boolean availableEmployeeBasedOnDateAndTime(TimeSlot theTimeSlot, Employee employee);
}