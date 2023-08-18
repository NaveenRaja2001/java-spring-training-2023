package com.example.meetingscheduler.controller;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.repository.TimeSlotRepository;
import com.example.meetingscheduler.service.EmployeeService;
import com.example.meetingscheduler.service.RoomService;
import com.example.meetingscheduler.service.TeamService;
import com.example.meetingscheduler.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class MeetingScheduleController {
    private TeamService teamService;
    private RoomService roomService;
    private TimeSlotService timeSlotService;
    private EmployeeService employeeService;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    public MeetingScheduleController(TeamService teamService, RoomService roomService, TimeSlotService timeSlotService, EmployeeService employeeService) {
        this.teamService = teamService;
        this.roomService = roomService;
        this.timeSlotService = timeSlotService;
        this.employeeService = employeeService;
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
        timeSlotService.save(timeSlot);
        return "Data added successfully   ('_')  ";
    }

    /**
     * This endpoint schedules the meeting for the team if the room is available for a given date anda time
     *
     * @param timeSlot   contains date ,startTime, EndTime and Description
     * @param employeeId employeeId who schedules the meeting
     * @param roomName   Room name for booking
     * @param teamid     meeting attenders
     * @return
     */
    @PostMapping("/create-meeting/{employeeId}/{roomName}")
    public String addMeeting(@RequestBody TimeSlot timeSlot, @PathVariable int employeeId, @PathVariable String roomName, @RequestParam Optional<Integer> teamid) {
        //meeting timing should  be minimum 30 minutes
        if (!timeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(timeSlot.getStartTime())) {
            throw new IllegalArgumentException("Minimum meeting time should be greater than 30 minus");
        }
        Room room = roomService.find(roomName);
        Teams givenTeam = null;
        timeSlot.addRoom(room);
        Employee employee = employeeService.findById(employeeId);
        timeSlot.setEmployee(employee);
        if (teamid.isPresent()) {
            givenTeam = teamService.find(teamid.get());
            timeSlot.addTeam(givenTeam);
        } else {
            List<Teams> teams = employee.getTeams();
            for (Teams team : teams) {
                timeSlot.addTeam(team);
            }
        }
        System.out.println(timeSlot);
        System.out.println(timeSlot.getTeams());
        System.out.println(timeSlot.getRooms());
        if (!roomAvailable(room, timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime())) {
            return "sorry room not available";
        } else {
            timeSlotService.save(timeSlot);
            return "Meeting Booked Successfully";
        }
    }

    /**
     * Checks whether the room is available or not on the given date and time
     *
     * @param room
     * @param date
     * @param startTime
     * @param endTime
     * @return boolean
     */
    private boolean roomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> timeSlots = timeSlotService.findRoomAvailableBasedOnTime(room, date, endTime, startTime);
        System.out.print(timeSlots);
        if (timeSlots.isEmpty()) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * This endpoint create the meeting apart from teams
     *
     * @param employees  List of employees to whom meeting need to be scheduled
     * @param timeSlot   contains date ,startTime, EndTime and Description
     * @param employeeId employeeId who schedules the meeting
     * @param roomName   Room name for booking
     * @return String
     */
    @PostMapping("/create-meeting/{employees}/{employeeId}/{roomName}")
    public String createNewTeam(@PathVariable List<Integer> employees, @RequestBody TimeSlot timeSlot, @PathVariable int employeeId, @PathVariable String roomName) {
        Room room = roomService.find(roomName);
        if (!timeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(timeSlot.getStartTime()) || (!roomAvailable(room, timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime()))) {
            return "Minimum meeting time should be greater than 30 minus  or room is not available in given time";
        }

        timeSlot.addRoom(room);
        Employee theEmployee = employeeService.findById(employeeId);
        timeSlot.setEmployee(theEmployee);
        Teams newTeam = new Teams("Temporary Team", employees.size());
        for (int i : employees) {
            Employee employee = employeeService.findById(i);

            newTeam.addEmployee(employee);
        }
        teamService.save(newTeam);
        timeSlot.addTeam(newTeam);
        timeSlotService.save(timeSlot);
        return "new team is and meeting is scheduled successfully";
    }

    @DeleteMapping("/delete-meeting")
    public String deleteMeeting(@RequestParam int id) {
        TimeSlot timeSlot = timeSlotService.find(id);
        LocalTime meetingBookedTime = timeSlot.getStartTime();
        LocalTime localTime = LocalTime.now();
        if (localTime.isBefore(meetingBookedTime.minus(30, ChronoUnit.MINUTES))) {
            timeSlotService.delete(id);
            return "TimeSlot is deleted successfully";
        } else {
            return "Can't delete the meeting at this moment";
        }
    }

    /**
     * This endpoint update the meeting date and time  based on the availability
     *
     * @param timeSlot contains date ,startTime, EndTime and Description which need to be updated
     * @param id       timeslotId which meeting to be updated
     * @return String
     */
    @PutMapping("/edit-meeting")
    public String updateMeeting(@RequestBody TimeSlot timeSlot, @RequestParam int id) {

        TimeSlot theTimeSlot = timeSlotService.find(id);
        List<Room> room = theTimeSlot.getRooms();
        if (!roomAvailable(room.get(0), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime())) {
            return "This Room is already booked in this timing";
        } else {
            theTimeSlot.setStartTime(timeSlot.getStartTime());
            theTimeSlot.setEndTime(timeSlot.getEndTime());
            timeSlotService.save(theTimeSlot);
            return "meeting timing is updated";
        }
    }

    /**
     * This endpoint update the meeting description
     *
     * @param description
     * @param timeslotId  contain id of the meeting which needed to be updated
     * @return String
     */
    @PutMapping("edit-meeting/{timeslotId}")
    public String updateMeetingDescription(@RequestParam String description, @PathVariable int timeslotId) {
        TimeSlot timeSlot = timeSlotService.find(timeslotId);
        timeSlot.setDescription(description);
        timeSlotService.save(timeSlot);
        return "Description updated Successfully";
    }

    /**
     * This endpoint add an employee to a meeting
     *
     * @param timeslotId id refers the meeting
     * @param employeeid id of employee needed to be added
     * @return String
     */
    @PutMapping("/edit-meeting/{timeslotId}/{employeeid}")
    public String updateMeetingAddEmployee(@PathVariable int timeslotId, @PathVariable int employeeid) {

        TimeSlot timeSlot = timeSlotService.find(timeslotId);
        Employee theEmployee = employeeService.findById(employeeid);

        List<Teams> teams = timeSlot.getTeams();
        List<Employee> employees = new ArrayList<>();
        for (Teams team : teams) {
            System.out.print(team);
            employees.addAll(team.getEmployees());
            team.setTimeSlots(null);
        }
        Teams newTeam = new Teams("edited team", employees.size() + 1);
        newTeam.addEmployee(theEmployee);
        for (Employee employee : employees) {
            newTeam.addEmployee(employee);
        }
        newTeam.addTimeSlot(timeSlot);
        teamService.save(newTeam);
        return "Employee has been added to the new meeting";

    }

    /**
     * This endpoint remove an employee to a meeting
     *
     * @param timeslotId id refers the meeting
     * @param employeeid id of employee needed to be removed
     * @return void
     */
    @PutMapping("/edit-meeting/remove/{timeslotId}/{employeeid}")
    public void updateMeetingRemoveEmployee(@PathVariable int timeslotId, @PathVariable int employeeid) {
        TimeSlot timeSlot = timeSlotService.find(timeslotId);
        List<Teams> teams = timeSlot.getTeams();
        List<Employee> employees = new ArrayList<>();
        for (Teams team : teams) {
            System.out.print(team);
            employees.addAll(team.getEmployees());
            team.setTimeSlots(null);
        }
        Teams newTeam = new Teams("edited team", employees.size() - 1);
        for (Employee employee : employees) {
            if (employee.getEmployeeId() == employeeid) {
                continue;
            }
            newTeam.addEmployee(employee);
        }
        newTeam.addTimeSlot(timeSlot);
        teamService.save(newTeam);
    }


}
