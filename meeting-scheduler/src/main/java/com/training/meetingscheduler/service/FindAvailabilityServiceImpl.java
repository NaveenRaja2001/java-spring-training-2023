package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.Teams;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.exception.MeetingScheduleException;
import com.training.meetingscheduler.projection.TimeSlotView;
import com.training.meetingscheduler.repository.RoomRepository;
import com.training.meetingscheduler.repository.TeamsRepository;
import com.training.meetingscheduler.repository.TimeSlotRepository;
import com.training.meetingscheduler.responseobject.TimeSlotResponse;
import com.training.meetingscheduler.serviceinterface.FindAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * THIS SERVICE SHOWS THE AVAILABILITY  BASED ON DATE AND TIME
 */
@Service
public class FindAvailabilityServiceImpl implements FindAvailabilityService {
    private TimeSlotRepository timeSlotRepository;
    private RoomRepository roomRepository;

    private TeamsRepository teamsRepository;

    @Autowired
    public FindAvailabilityServiceImpl(TimeSlotRepository timeSlotRepository, RoomRepository roomRepository, TeamsRepository teamsRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.roomRepository = roomRepository;
        this.teamsRepository = teamsRepository;
    }

    /**
     * Return the room based on the team count
     *
     * @param reqTimeSlot
     * @param teamId
     * @param teamCount
     * @return
     */
    @Override
    public Map<String, Integer> availableRoomsBasedOnDateAndTime(TimeSlot reqTimeSlot, Optional<Integer> teamCount) {
        HashMap<String, Integer> sortedAvailableRoom = null;
        List<Room> bookedRoom = new ArrayList<>();
        List<Integer> roomId = new ArrayList<>();

        try {
            LocalDate reqDate = reqTimeSlot.getDate();
            List<TimeSlot> timeSlots = timeSlotRepository.findTimeSlotByDate(reqDate);
            LocalTime reqStartTime = reqTimeSlot.getStartTime();
            LocalTime reqEndTime = reqTimeSlot.getEndTime();
            timeSlots.stream().forEach(timeSlot -> {
                if ((reqStartTime.isAfter(timeSlot.getStartTime()) && reqStartTime.isBefore(timeSlot.getEndTime())) || (reqEndTime.isAfter(timeSlot.getStartTime()) && reqEndTime.isBefore(timeSlot.getEndTime()))) {
                    bookedRoom.addAll(timeSlot.getRooms());
                }
            });
            List<Room> availableRoom = roomRepository.findAll();
            availableRoom.removeAll(bookedRoom);
            Map<String, Integer> availableRoomNames = availableRoom.stream().collect(Collectors.toMap(Room::getRoomName, Room::getRoomCapacity));
            sortedAvailableRoom = sortByValue(availableRoomNames);
            if (teamCount.isPresent()) {
                for (Map.Entry<String, Integer> entry : sortedAvailableRoom.entrySet()) {
                    if (entry.getValue() >= teamCount.get()) {
                        return Map.of(entry.getKey(), entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
        return sortedAvailableRoom;
    }

    /**
     * This method sorts the hashmap based on the value
     *
     * @param array
     * @return Sorted Hashmap
     */
    @Override
    public HashMap<String, Integer> sortByValue(Map<String, Integer> array) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(array.entrySet());
        Collections.sort(list, (i1, i2) -> i1.getValue().compareTo(i2.getValue()));
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        list.forEach(aa -> temp.put(aa.getKey(), aa.getValue()));
        return temp;
    }

    /**
     * This method return the available employees  based on dateAndTime
     *
     * @param theTimeSlot
     * @param teamId
     * @return
     */
    @Override
    public Map<Integer, Boolean> availableEmployeesBasedOnDateAndTime(TimeSlot theTimeSlot, int teamId) {
        Map<Integer, Boolean> availableEmployeeStatus = new HashMap<>();
        try {
            Teams team = teamsRepository.findById(teamId).orElseThrow(() -> new MeetingScheduleException("Team Id Not found"));
            List<Employee> employees = team.getEmployees();
            for (Employee employee : employees) {
                availableEmployeeStatus.put(employee.getEmployeeId(),
                        availableEmployeeBasedOnDateAndTime(theTimeSlot, employee));
            }
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
        return availableEmployeeStatus;
    }

    /**
     * This method return the available individual employee  based on dateAndTime
     *
     * @param reqTimeSlot
     * @param employee
     * @return
     */
    @Override
    public boolean availableEmployeeBasedOnDateAndTime(TimeSlot reqTimeSlot, Employee employee) {
        LocalDate reqDate = reqTimeSlot.getDate();
        LocalTime reqStartTime = reqTimeSlot.getStartTime();
        LocalTime reqEndTime = reqTimeSlot.getEndTime();
        List<Teams> teams = employee.getTeams();
        List<TimeSlot> timeSlots = teams.stream().flatMap(individualTeam -> individualTeam.getTimeSlots().stream()).collect(Collectors.toList());
        return timeSlots.stream().anyMatch(timeSlot -> {
            if ((reqDate.isEqual(timeSlot.getDate())) && ((reqStartTime.isAfter(timeSlot.getStartTime()) && reqStartTime.isBefore(timeSlot.getEndTime())) || (reqEndTime.isAfter(timeSlot.getStartTime()) && reqEndTime.isBefore(timeSlot.getEndTime())))) {
                return false;
            }
            return true;
        });
    }
}
