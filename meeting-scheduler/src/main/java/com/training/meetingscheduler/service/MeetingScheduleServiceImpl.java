package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.entity.Room;
import com.training.meetingscheduler.entity.Teams;
import com.training.meetingscheduler.entity.TimeSlot;
import com.training.meetingscheduler.exception.MeetingScheduleException;
import com.training.meetingscheduler.repository.EmployeeRepository;
import com.training.meetingscheduler.repository.RoomRepository;
import com.training.meetingscheduler.repository.TeamsRepository;
import com.training.meetingscheduler.repository.TimeSlotRepository;
import com.training.meetingscheduler.requestobject.CollaborationRequest;
import com.training.meetingscheduler.responseobject.RoomsResponse;
import com.training.meetingscheduler.responseobject.TeamsResponse;
import com.training.meetingscheduler.responseobject.TimeSlotResponse;
import com.training.meetingscheduler.serviceinterface.MeetingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MeetingScheduleServiceImpl implements MeetingScheduleService {

    private RoomRepository roomRepository;
    private EmployeeRepository employeeRepository;

    private TeamsRepository teamsRepository;

    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public MeetingScheduleServiceImpl(RoomRepository roomRepository, EmployeeRepository employeeRepository, TeamsRepository teamsRepository, TimeSlotRepository timeSlotRepository) {
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
        this.teamsRepository = teamsRepository;
        this.timeSlotRepository = timeSlotRepository;
    }


    /**
     * This endpoint is used to create a meeting with available rooms based on date and time
     *
     * @param reqTimeSlot contains date ,startTime, EndTime and Description
     * @param employeeId  employeeId who schedules the meeting
     * @param reqRoomName Room name for booking
     * @param reqTeamId   meeting attenders
     * @return TimeSlotResponse
     */
    @Override
    public TimeSlotResponse addMeeting(TimeSlot reqTimeSlot, int employeeId, String reqRoomName, Optional<Integer> reqTeamId) {
        //meeting timing should  be minimum 30 minutes
        if (!reqTimeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(reqTimeSlot.getStartTime())) {
            throw new MeetingScheduleException("Minimum meeting time should be greater than 30 minus");
        }
        Room room = roomRepository.findByroomName(reqRoomName);
        Teams givenTeam = null;
        int value = 0;
        reqTimeSlot.addRoom(room);
        Employee employee = employeeRepository.findById(employeeId);
        reqTimeSlot.setEmployee(employee);
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse(reqTimeSlot.getTimeSlotId(), reqTimeSlot.getDescription(), reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime());
        if (reqTeamId.isPresent()) {
            value = reqTeamId.get();
            givenTeam = teamsRepository.findById(value);
            reqTimeSlot.addTeam(givenTeam);
            TeamsResponse teamsResponse = new TeamsResponse(value, givenTeam.getTeamCount(), givenTeam.getTeamName());
            timeSlotResponse.setTeams(List.of(teamsResponse));
        } else {
            List<Teams> teams = employee.getTeams();
            teams.stream().forEach(team -> {
                reqTimeSlot.addTeam(team);
                timeSlotResponse.setTeams(List.of(new TeamsResponse(team.getTeamId(), team.getTeamCount(), team.getTeamName())));
            });

//            for (Teams team : teams) {
//                reqTimeSlot.addTeam(team);
//                timeSlotResponse.setTeams(List.of(new TeamsResponse(team.getTeamId(), team.getTeamCount(), team.getTeamName())));
//            }
        }
        if (!roomAvailable(room, reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime())) {
            throw new MeetingScheduleException("sorry room is not available");
        } else {
            timeSlotRepository.save(reqTimeSlot);
            RoomsResponse roomsResponse = new RoomsResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
            timeSlotResponse.setRooms(List.of(roomsResponse));
            return timeSlotResponse;
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
    @Override
    public boolean roomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(room, date, endTime, startTime);

        System.out.print(timeSlots);
        if (timeSlots.isEmpty()) {
            return true;
        } else {
            return false;
        }


    }


    /**
     * This endpoint is used to create a meeting apart from team with available rooms based on date and time
     *
     * @param collaborationRequest contains timeslot details with List of employee id
     * @param employeeId           id of employee who creates the meeting
     * @param roomName             Room name for booking
     * @return
     */
    @Override
    public TimeSlotResponse addMeeting(CollaborationRequest collaborationRequest, int employeeId, String roomName) {
        TimeSlot reqTimeSlot = new TimeSlot(collaborationRequest.getLocalDate(), collaborationRequest.getStartTime(), collaborationRequest.getEndTime(), collaborationRequest.getDescription());
        Room room = roomRepository.findByroomName(roomName);
        if (!reqTimeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(reqTimeSlot.getStartTime()) || (!roomAvailable(room, reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime()))) {
            throw new MeetingScheduleException("Minimum meeting time should be greater than 30 minus  or room is not available in given time");
        }

        reqTimeSlot.addRoom(room);
        Employee theEmployee = employeeRepository.findById(employeeId);
        reqTimeSlot.setEmployee(theEmployee);
        Teams newTeam = new Teams("Temporary Team", collaborationRequest.getEmployeeList().size());
        newTeam.addEmployee(theEmployee);
        for (int i : collaborationRequest.getEmployeeList()) {
            Employee employee = employeeRepository.findById(i);
            newTeam.addEmployee(employee);
        }
        teamsRepository.save(newTeam);
        reqTimeSlot.addTeam(newTeam);
        timeSlotRepository.save(reqTimeSlot);
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse(reqTimeSlot.getTimeSlotId(), reqTimeSlot.getDescription(), reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime());
        RoomsResponse roomsResponse = new RoomsResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
        TeamsResponse teamsResponse = new TeamsResponse(newTeam.getTeamId(), newTeam.getTeamCount(), newTeam.getTeamName());
        timeSlotResponse.setRooms(List.of(roomsResponse));
        timeSlotResponse.setTeams(List.of(teamsResponse));
        return timeSlotResponse;
    }

    /**
     * this service delete the timeslot based on id
     *
     * @param id
     * @return TimeSlotResponse
     */
    @Override
    public TimeSlotResponse deleteMeeting(int id) {

        TimeSlot reqTimeSlot = timeSlotRepository.findById(id);
        if (reqTimeSlot == null) {
            throw new MeetingScheduleException("TimeSlot Id Invalid");
        }
        LocalTime meetingBookedTime = reqTimeSlot.getStartTime();
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        if (!((localDate.isBefore(reqTimeSlot.getDate())) ||
                (localDate.equals(reqTimeSlot.getDate())
                        && localTime.isBefore(meetingBookedTime.minus(30, ChronoUnit.MINUTES))))) {
            throw new MeetingScheduleException("Oops Time Passed....Can't delete the meeting at this moment");

        } else {
            TimeSlotResponse timeSlotResponse = new TimeSlotResponse(reqTimeSlot.getTimeSlotId(), reqTimeSlot.getDescription(), reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime());
            timeSlotRepository.deleteById(id);
            return timeSlotResponse;
        }
    }

    /**
     * This method edit the timeslots date and time  based on availability
     *
     * @param timeSlot
     * @param id
     * @return TimeSlotResponse
     */
    @Override
    public TimeSlotResponse updateMeeting(TimeSlot timeSlot, int id) {
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse();
        TimeSlot theTimeSlot = timeSlotRepository.findById(id);
        List<Room> room = theTimeSlot.getRooms();
        if (timeSlot.getDate() == null) {
            theTimeSlot.setDescription(timeSlot.getDescription());
            timeSlotResponse.setGetTimeSlotId(theTimeSlot.getTimeSlotId());
            timeSlotResponse.setStartTime(theTimeSlot.getStartTime());
            timeSlotResponse.setEndTime(theTimeSlot.getEndTime());
            timeSlotResponse.setLocalDate(theTimeSlot.getDate());
            timeSlotResponse.setGetDescription(timeSlot.getDescription());
            timeSlotRepository.save(theTimeSlot);
            return timeSlotResponse;
        }
        if (!roomAvailable(room.get(0), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime())) {
            throw new MeetingScheduleException("This Room is already booked in this timing");
        } else {
            theTimeSlot.setStartTime(timeSlot.getStartTime());
            theTimeSlot.setEndTime(timeSlot.getEndTime());
            theTimeSlot.setDescription(timeSlot.getDescription());
            timeSlotRepository.save(theTimeSlot);
            timeSlotResponse.setGetTimeSlotId(theTimeSlot.getTimeSlotId());
            timeSlotResponse.setStartTime(theTimeSlot.getStartTime());
            timeSlotResponse.setEndTime(theTimeSlot.getEndTime());
            timeSlotResponse.setGetDescription(theTimeSlot.getDescription());
            timeSlotResponse.setLocalDate(theTimeSlot.getDate());
            return timeSlotResponse;
        }
    }

    /**
     * This method edit the description of the meeting
     *
     * @param description
     * @param timeslotId
     * @return String
     */
    @Override
    public String updateMeetingDescription(String description, int timeslotId) {

        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId);
        timeSlot.setDescription(description);
        timeSlotRepository.save(timeSlot);
        return "Description updated Successfully";
    }


    /**
     * This method is used to add an employee in scheduled meeting
     *
     * @param timeslotId
     * @param employeeId
     * @return TimeSlotResponse
     */
    @Override
    public TimeSlotResponse updateMeetingAddEmployee(int timeslotId, int employeeId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId);
        Employee theEmployee = employeeRepository.findById(employeeId);

        if (timeSlot == null || theEmployee == null) {
            throw new MeetingScheduleException("Invalid TimeSlot id or Employeed iD");
        }
        List<Teams> teams = timeSlot.getTeams();
        List<Employee> employees = new ArrayList<>();
        teams.stream().forEach(team -> {
            employees.addAll(team.getEmployees());
            team.setTimeSlots(null);
        });
//        for (Teams team : teams) {
//            System.out.print(team);
//            employees.addAll(team.getEmployees());
//            team.setTimeSlots(null);
//        }
        Teams newTeam = new Teams("edited team", employees.size() + 1);
        newTeam.addEmployee(theEmployee);
        employees.stream().forEach(employee -> {
            if (employee.getEmployeeId() == employeeId) {
                throw new MeetingScheduleException("Employee is already in the meeting");
            }
            newTeam.addEmployee(employee);
        });
//        for (Employee employee : employees) {
//            if(employee.getEmployeeId()==employeeId){
//                throw new MeetingScheduleException("Employee is already in the meeting");
//            }
//            newTeam.addEmployee(employee);
//        }
        newTeam.addTimeSlot(timeSlot);
        teamsRepository.save(newTeam);
        TeamsResponse teamsResponse = new TeamsResponse(newTeam.getTeamId(), newTeam.getTeamCount(), newTeam.getTeamName());
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime());
        timeSlotResponse.setTeams(List.of(teamsResponse));
        return timeSlotResponse;
    }

    /**
     * This method is used to remove an employee in scheduled meeting
     *
     * @param timeslotId
     * @param employeeId
     * @return TimeSlotResponse
     */
    @Override
    public TimeSlotResponse updateMeetingRemoveEmployee(int timeslotId, int employeeId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId);
        if (timeSlot == null) {
            throw new MeetingScheduleException("Invalid TimeSlot id or Employeed iD");
        }
        List<Teams> teams = timeSlot.getTeams();
        Set<Employee> reqEmployee = new HashSet<>();

        teams.stream().forEach(team -> {
            reqEmployee.addAll(team.getEmployees());
            team.setTimeSlots(null);
        });
        if(!reqEmployee.contains(employeeRepository.findById(employeeId))){
            throw new MeetingScheduleException("No employee Id found in the meeting");
        }
//        for (Teams team : teams) {
//            reqEmployee.addAll(team.getEmployees());
//            team.setTimeSlots(null);
//        }
        Teams newTeam = new Teams("edited team", reqEmployee.size() - 1);
//        reqEmployee.stream().forEach(employee -> newTeam.addEmployee(employee));
        for (Employee employee : reqEmployee) {
            if(employee.getEmployeeId()==employeeId){
                continue;
            }
            newTeam.addEmployee(employee);
        }
        newTeam.addTimeSlot(timeSlot);
        teamsRepository.save(newTeam);
        TeamsResponse teamsResponse = new TeamsResponse(newTeam.getTeamId(), newTeam.getTeamCount(), newTeam.getTeamName());
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime());
        timeSlotResponse.setTeams(List.of(teamsResponse));
        return timeSlotResponse;
    }
}



