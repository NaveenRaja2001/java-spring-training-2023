package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.projection.TimeSlotView;
import com.example.meetingscheduler.repository.RoomRepository;
import com.example.meetingscheduler.repository.TeamsRepository;
import com.example.meetingscheduler.repository.TimeSlotRepository;
import com.example.meetingscheduler.responseObject.TimeSlotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
     * @param theTimeSlot
     * @param teamId
     * @param teamCount
     * @return
     */
    @Override
    public Map<String, Integer> availableRoomsBasedOnDateAndTime(TimeSlot theTimeSlot, Optional<Integer> teamId, Optional<Integer> teamCount) {
        LocalDate date1 = theTimeSlot.getDate();
        List<TimeSlotView> timeSlots = timeSlotRepository.findTimeSlotByDate(date1);
        List<TimeSlotResponse> timeSlotResponses = new ArrayList<>();
        List<Room> bookedRoom = new ArrayList<>();
        List<Integer> roomId = new ArrayList<>();
        LocalTime tempStartTime = theTimeSlot.getStartTime();
        LocalTime tempEndTime = theTimeSlot.getEndTime();
        for (TimeSlotView timeSlot : timeSlots) {
            if ((tempStartTime.isAfter(timeSlot.getstartTime()) && tempStartTime.isBefore(timeSlot.getendTime())) || (tempEndTime.isAfter(timeSlot.getstartTime()) && tempEndTime.isBefore(timeSlot.getendTime()))) {
                bookedRoom.addAll(timeSlot.getRooms());
                timeSlotResponses.add(new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getstartTime(), timeSlot.getendTime()));
            }
        }
        HashMap<String, Integer> availableRoomNames = new HashMap();
        List<Room> avaliableRoom = roomRepository.findAll();
        avaliableRoom.removeAll(bookedRoom);
        for (Room room : avaliableRoom) {
            availableRoomNames.put(room.getRoomName(), room.getRoomCapacity());
        }
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
        List<Employee> employees = team.getEmployees();
        HashMap<Integer, Boolean> availableEmployeeStatus = new HashMap<>();
        for (Employee employee : employees) {
            availableEmployeeStatus.put(employee.getEmployeeId(), availableEmployeeBasedOnDateAndTime(theTimeSlot, employee));
        }
        return availableEmployeeStatus;
    }

    /**
     * This method return the available individual employee  based on dateAndTime
     *
     * @param theTimeSlot
     * @param employee
     * @return
     */
    @Override
    public boolean availableEmployeeBasedOnDateAndTime(TimeSlot theTimeSlot, Employee employee) {
        LocalDate date = theTimeSlot.getDate();
        LocalTime tempStartTime = theTimeSlot.getStartTime();
        LocalTime tempEndTime = theTimeSlot.getEndTime();

        List<Teams> teams = employee.getTeams();
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (Teams teams1 : teams) {
            timeSlots.addAll(teams1.getTimeSlots());
        }
        for (TimeSlot timeSlot : timeSlots) {
            System.out.print(tempEndTime);
            System.out.print(timeSlot.getStartTime());
            System.out.print(timeSlot.getEndTime());
            System.out.print((tempEndTime.isAfter(timeSlot.getStartTime()) && tempEndTime.isBefore(timeSlot.getEndTime())));
            if ((date.isEqual(timeSlot.getDate())) && ((tempStartTime.isAfter(timeSlot.getStartTime()) && tempStartTime.isBefore(timeSlot.getEndTime())) || (tempEndTime.isAfter(timeSlot.getStartTime()) && tempEndTime.isBefore(timeSlot.getEndTime())))) {
                return false;
            }
        }
        return true;


    }

}
