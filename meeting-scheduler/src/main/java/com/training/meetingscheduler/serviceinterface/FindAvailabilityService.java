package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.TimeSlot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface FindAvailabilityService{
    public Map<String, Integer> availableRoomsBasedOnDateAndTime(TimeSlot reqTimeSlot,Optional<Integer> teamCount);

    public  HashMap<String, Integer> sortByValue(Map<String, Integer> array);
    public Map<Integer, Boolean> availableEmployeesBasedOnDateAndTime(TimeSlot theTimeSlot, int teamId);
    public boolean availableEmployeeBasedOnDateAndTime(TimeSlot theTimeSlot, Employee employee);
}