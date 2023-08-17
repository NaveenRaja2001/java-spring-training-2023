package com.example.meetingscheduler.controller;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.projection.TimeSlotView;
import com.example.meetingscheduler.responseObject.TimeSlotResponse;
import com.example.meetingscheduler.service.RoomService;
import com.example.meetingscheduler.service.TeamService;
import com.example.meetingscheduler.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@RestController
@RequestMapping("meeting-schedule")
public class MeetingScheduleController {

    TimeSlotService timeSlotService;
    RoomService roomService;
    @Autowired
    TeamService teamService;
    @Autowired
    public MeetingScheduleController(TimeSlotService timeSlotService, RoomService roomService) {
        this.timeSlotService = timeSlotService;
        this.roomService = roomService;
    }

    /**
     *
     * @param theTimeSlot
     * @param teamId
     * @param teamCount
     * @return
     */
    @GetMapping("/findrooms")
    public Map<String, Integer> availableRoomsBasedOnDateAndTime(@RequestBody TimeSlot theTimeSlot, @RequestParam Optional<Integer> teamId,@RequestParam Optional<Integer> teamCount) {
        LocalDate date1 = theTimeSlot.getDate();
        List<TimeSlotView> timeSlots = timeSlotService.findTimeSlotIdByDate(date1);
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
        List<Room> avaliableRoom = roomService.findAll();
        avaliableRoom.removeAll(bookedRoom);
        for (Room room : avaliableRoom) {
            availableRoomNames.put(room.getRoomName(), room.getRoomCapacity());
        }
        HashMap<String, Integer> sortedAvailableRoom = null;

        sortedAvailableRoom = sortByValue(availableRoomNames);

        if (teamId.isPresent() || teamCount.isPresent()) {
            int teamSize=0;
            if(teamId.isPresent()) {
                Teams teams = teamService.find(teamId.get());
               teamSize= teams.getTeamCount();
            }else {
                teamSize=teamCount.get();
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
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> array) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(array.entrySet());

        Collections.sort(list, (i1, i2) -> i1.getValue().compareTo(i2.getValue()));

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    @GetMapping("/findEmployees")
    public void availableEmployeesBasedOnDateAndTime(){
   


    }



}
