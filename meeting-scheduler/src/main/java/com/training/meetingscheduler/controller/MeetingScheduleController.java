package com.training.meetingscheduler.controller;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.Teams;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.repository.TimeSlotRepository;
import com.training.meetingscheduler.requestobject.CollaborationRequest;
import com.training.meetingscheduler.responseobject.TimeSlotResponse;

import com.training.meetingscheduler.service.*;
import com.training.meetingscheduler.serviceinterface.EmployeeService;
import com.training.meetingscheduler.serviceinterface.RoomService;
import com.training.meetingscheduler.serviceinterface.TeamService;
import com.training.meetingscheduler.serviceinterface.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("meeting")
public class MeetingScheduleController {
    @Autowired
    private TeamService teamService;
    private RoomService roomService;
    private TimeSlotService timeSlotService;
    private EmployeeService employeeService;


   private MeetingScheduleServiceImpl meetingScheduleService;


   private TimeSlotRepository timeSlotRepository;

@Autowired
    public MeetingScheduleController( RoomService roomService, TimeSlotService timeSlotService, EmployeeService employeeService, MeetingScheduleServiceImpl meetingScheduleService, TimeSlotRepository timeSlotRepository) {
//        this.teamService = teamService;
        this.roomService = roomService;
        this.timeSlotService = timeSlotService;
        this.employeeService = employeeService;
        this.meetingScheduleService = meetingScheduleService;
        this.timeSlotRepository = timeSlotRepository;
    }




    /**
     * add mock data to table
     *
     * @return String
     */
    @GetMapping("/addData")
    public String addTeams() {
        Employee e1 = new Employee("Naveen", "N", "nav@gmail.com");
        Employee e2 = new Employee("hari", "S", "hari@gmail.com");
        Employee e3 = new Employee("ashok", "A", "ashok@gmail.com");
        Employee e4 = new Employee("sathish", "R", "sathish@gmail.com");
        Employee e5 = new Employee("palani", "P", "palani@gmail.com");
        Employee e6 = new Employee("kavya", "K", "kavya@gmail.com");
        Employee e7 = new Employee("pavithra", "S", "pavithra@gmail.com");
        Employee e8 = new Employee("thendral", "S", "thendral@gmail.com");
        Employee e9 = new Employee("bidda", "S", "bidda@gmail.com");
        Employee e10 = new Employee("mugilan", "S", "mugilan@gmail.com");

        Teams t1 = new Teams("Software Engineering", 3);
        t1.addEmployee(e1);
        t1.addEmployee(e2);
        t1.addEmployee(e3);
        Teams t2 = new Teams("IP", 2);
        t2.addEmployee(e4);
        t2.addEmployee(e5);
        Teams t3 = new Teams("Data", 1);
        t3.addEmployee(e6);
        Teams t4 = new Teams("Snow", 6);
        t4.addEmployee(e7);
        t4.addEmployee(e8);
        t4.addEmployee(e9);
        t4.addEmployee(e10);


        teamService.save(t1);
        teamService.save(t2);
        teamService.save(t3);
        teamService.save(t4);

        Room r1 = new Room("TrainingRoom", 12);
        Room r2 = new Room("Chennai", 3);

        Room r3 = new Room("Austin", 6);
        Room r4 = new Room("Sydney", 17);
        Room r5 = new Room("Tanjore", 40);
        Room r6 = new Room("London", 5);


        roomService.save(r1);
        roomService.save(r2);
        roomService.save(r3);
        roomService.save(r4);
        roomService.save(r5);
        roomService.save(r6);
        LocalDate localDate = LocalDate.parse("2023-08-15");
        LocalTime startTime = LocalTime.parse("10:15:45");
        LocalTime lastTime = LocalTime.parse("12:15:00");
        TimeSlot timeSlot = new TimeSlot(localDate, startTime, startTime, "regarding UIBC competition");
        timeSlot.setEmployee(e6);
        timeSlot.addRoom(r4);
        timeSlot.addTeam(t3);

        System.out.println(timeSlot);
        System.out.println(timeSlot.getTeams());
        System.out.println(timeSlot.getRooms());
        timeSlotRepository.save(timeSlot);
        return "Data added successfully   ('_')  ";
    }

    /**
     * This endpoint schedules the meeting for the team if the room is available for a given date anda time
     *
     * @param timeSlot   contains date ,startTime, EndTime and Description
     * @param employeeId employeeId who schedules the meeting
     * @param roomName   Room name for booking
     * @param teamId     meeting attenders
     * @return
     */
    @PostMapping("/team/{employeeId}/{roomName}")
    public ResponseEntity<TimeSlotResponse> addMeeting(@RequestBody TimeSlot timeSlot, @PathVariable int employeeId, @PathVariable String roomName, @RequestParam Optional<Integer> teamId){
        TimeSlotResponse value=meetingScheduleService.addMeeting(timeSlot,employeeId,roomName,teamId);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }


    /**
     * This endpoint create the meeting apart from teams
     *
     * @param collaborationRequest contains timeslot details with List of employee id
     * @param employeeId id of employee who creates the meeting
     * @param roomName Room name for booking
     * @return
     */
    @PostMapping("/collaboration/{employeeId}/{roomName}")
    public ResponseEntity<TimeSlotResponse> addMeeting(@RequestBody CollaborationRequest collaborationRequest, @PathVariable int employeeId, @PathVariable String roomName){
        TimeSlotResponse value= meetingScheduleService.addMeeting(collaborationRequest,employeeId,roomName);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }


    /**
     * This endpoint delete the meeting
     * @param timeSlotId id of timeSlot which needed to be deleted
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlotResponse> deleteMeeting(@PathVariable int timeSlotId){
        TimeSlotResponse value= meetingScheduleService.deleteMeeting(timeSlotId);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }

    /**
     * This endpoint update the meeting date and time  based on the availability
     *
     * @param timeSlot contains date ,startTime, EndTime and Description which need to be updated
     * @param id       timeslotId which meeting to be updated
     * @return String
     */
    @PatchMapping("/{id}")
    public  ResponseEntity<TimeSlotResponse> updateMeeting(@RequestBody TimeSlot timeSlot,@PathVariable int id) {
        TimeSlotResponse value=meetingScheduleService.updateMeeting(timeSlot,id);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }

    /**
     * This endpoint update the meeting description
     *
     * @param description
     * @param timeslotId  contain id of the meeting which needed to be updated
     * @return String
     */
//    @PatchMapping("")
//    public  ResponseEntity<String> updateMeetingDescription(@RequestParam String description, @RequestParam int timeslotId) {
//        String value= meetingScheduleService.updateMeetingDescription(description,timeslotId);
//        return ResponseEntity.status(HttpStatus.OK).body(value);
//    }

    /**
     * This endpoint add an employee to a meeting
     *
     * @param timeslotId id refers the meeting
     * @param employeeId id of employee needed to be added
     * @return String
     */
    @PutMapping("/{timeslotId}/{employeeId}")
    public  ResponseEntity<TimeSlotResponse> updateMeetingAddEmployee(@PathVariable int timeslotId, @PathVariable int employeeId) {
        TimeSlotResponse value= meetingScheduleService.updateMeetingAddEmployee(timeslotId,employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }




    /**
     * This endpoint remove an employee to a meeting
     *
     * @param timeslotId id refers the meeting
     * @param employeeId id of employee needed to be removed
     * @return void
     */

    @PutMapping("/remove/{timeslotId}/{employeeId}")
    public ResponseEntity<TimeSlotResponse> updateMeetingRemoveEmployee(@PathVariable int timeslotId, @PathVariable int employeeId) {
        TimeSlotResponse value = meetingScheduleService.updateMeetingRemoveEmployee(timeslotId,employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }


}
