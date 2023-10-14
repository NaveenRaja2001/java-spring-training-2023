package com.training.meetingscheduler.controller;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.service.*;
import com.training.meetingscheduler.serviceinterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


/**
 * THIS CONTROLLER GIVEN AVAILABILITY OF ROOM AND EMPLOYEE BASED ON DATE AND TIME
 */
@RestController
@RequestMapping("meeting-schedule")
public class FindAvailabilityController {

    private TimeSlotService timeSlotService;
    private RoomService roomService;
    private EmployeeService employeeService;
    private TeamService teamService;
    private FindAvailabilityService findAvailabilityService;

    @Autowired
    public FindAvailabilityController(TimeSlotService timeSlotService, RoomService roomService, EmployeeService employeeService, TeamService teamService, FindAvailabilityServiceImpl findAvailabilityService) {
        this.timeSlotService = timeSlotService;
        this.roomService = roomService;
        this.employeeService = employeeService;
        this.teamService = teamService;
        this.findAvailabilityService = findAvailabilityService;
    }

    /**
     * Find the available room based on Date and Time
     * @param theTimeSlot
     * @param teamCount
     * @return
     */

    @GetMapping("/rooms")
    public ResponseEntity<Map<String, Integer>> availableRoomsBasedOnDateAndTime(@RequestBody TimeSlot theTimeSlot, @RequestParam Optional<Integer> teamCount) {
        Map<String, Integer> response = findAvailabilityService.availableRoomsBasedOnDateAndTime(theTimeSlot,teamCount);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * Find the availability of the employee in team based on Date and Time
     *
     * @param theTimeSlot
     * @param teamId
     * @return
     */
    @GetMapping("/employees")
    public ResponseEntity<Map<Integer, Boolean>> availableEmployeesBasedOnDateAndTime(@RequestBody TimeSlot theTimeSlot, @RequestParam int teamId) {
        Map<Integer, Boolean> response = findAvailabilityService.availableEmployeesBasedOnDateAndTime(theTimeSlot, teamId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
