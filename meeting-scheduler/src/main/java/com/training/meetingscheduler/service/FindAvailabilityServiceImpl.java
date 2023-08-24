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
import java.util.*;

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
    public Map<String, Integer> availableRoomsBasedOnDateAndTime(TimeSlot reqTimeSlot, Optional<Integer> teamId, Optional<Integer> teamCount) {
        LocalDate reqDate = reqTimeSlot.getDate();
        List<TimeSlotView> timeSlots = timeSlotRepository.findTimeSlotByDate(reqDate);
        List<TimeSlotResponse> timeSlotResponses = new ArrayList<>();
        List<Room> bookedRoom = new ArrayList<>();
        List<Integer> roomId = new ArrayList<>();
        LocalTime reqStartTime = reqTimeSlot.getStartTime();
        LocalTime reqEndTime = reqTimeSlot.getEndTime();
        timeSlots.stream().forEach(timeSlot -> {
            if ((reqStartTime.isAfter(timeSlot.getstartTime())
                    && reqStartTime.isBefore(timeSlot.getendTime()))
                    || (reqEndTime.isAfter(timeSlot.getstartTime())
                    && reqEndTime.isBefore(timeSlot.getendTime()))) {
                bookedRoom.addAll(timeSlot.getRooms());
                timeSlotResponses.add(new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getstartTime(), timeSlot.getendTime()));
            }
        });
//        for (TimeSlotView timeSlot : timeSlots) {
//            if ((reqStartTime.isAfter(timeSlot.getstartTime())
//                    && reqStartTime.isBefore(timeSlot.getendTime()))
//                    || (reqEndTime.isAfter(timeSlot.getstartTime())
//                    && reqEndTime.isBefore(timeSlot.getendTime()))) {
//                bookedRoom.addAll(timeSlot.getRooms());
//                timeSlotResponses.add(new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getstartTime(), timeSlot.getendTime()));
//            }
//        }
        HashMap<String, Integer> availableRoomNames = new HashMap();
        List<Room> availableRoom = roomRepository.findAll();
        availableRoom.removeAll(bookedRoom);
        availableRoom.stream().forEach(room -> {
            availableRoomNames.put(room.getRoomName(), room.getRoomCapacity());
        });

//        for (Room room : availableRoom) {
//            availableRoomNames.put(room.getRoomName(), room.getRoomCapacity());
//        }
        HashMap<String, Integer> sortedAvailableRoom = null;
        sortedAvailableRoom = sortByValue(availableRoomNames);
        if (teamId.isPresent() || teamCount.isPresent()) {
            int teamSize = 0;
            if (teamId.isPresent()) {
                int value = teamId.get();
                Teams teams = teamsRepository.findById(value);
                teamSize = teams.getTeamCount();
            } else {
                teamSize = teamCount.get();
            }

            for (Map.Entry<String, Integer> entry : sortedAvailableRoom.entrySet()) {
                if (entry.getValue() >= teamSize) {
                    availableRoomNames.clear();
                    System.out.println(availableRoomNames);
                    availableRoomNames.put(entry.getKey(), entry.getValue());
                    System.out.println(availableRoomNames);
                    return availableRoomNames;
                }
            }
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
    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> array) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(array.entrySet());
        Collections.sort(list, (i1, i2) -> i1.getValue().compareTo(i2.getValue()));
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
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
    public HashMap<Integer, Boolean> availableEmployeesBasedOnDateAndTime(TimeSlot theTimeSlot, int teamId) {
        Teams team = teamsRepository.findById(teamId);
        if (team == null) {
            throw new MeetingScheduleException("TEAM NOT FOUND");
        }
        List<Employee> employees = team.getEmployees();
        HashMap<Integer, Boolean> availableEmployeeStatus = new HashMap<>();
        employees.stream().forEach(employee -> {
            availableEmployeeStatus.put(employee.getEmployeeId(),
                    availableEmployeeBasedOnDateAndTime(theTimeSlot, employee));
        });
//        for (Employee employee : employees) {
//            availableEmployeeStatus.put(employee.getEmployeeId(),
//                    availableEmployeeBasedOnDateAndTime(theTimeSlot, employee));
//        }
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
        List<TimeSlot> timeSlots = new ArrayList<>();
        teams.stream().forEach(individualTeam -> {
            timeSlots.addAll(individualTeam.getTimeSlots());
        });
        for (TimeSlot timeSlot : timeSlots) {
            if ((reqDate.isEqual(timeSlot.getDate()))
                    && ((reqStartTime.isAfter(timeSlot.getStartTime())
                    && reqStartTime.isBefore(timeSlot.getEndTime()))
                    || (reqEndTime.isAfter(timeSlot.getStartTime())
                    && reqEndTime.isBefore(timeSlot.getEndTime())))) {
                return false;
            }
        }
        return true;
    }

}
