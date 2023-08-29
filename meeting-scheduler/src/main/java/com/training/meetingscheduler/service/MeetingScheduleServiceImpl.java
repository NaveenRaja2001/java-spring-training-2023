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
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        TimeSlotResponse timeSlotResponse = null;
        Optional<Teams> givenTeam = null;
        int value = 0;
        try {
            Room room = roomRepository.findByroomName(reqRoomName);
            //meeting timing should  be minimum 30 minutes
            //check the room is available or not
            if (!reqTimeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(reqTimeSlot.getStartTime()) || !roomAvailable(room, reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime())) {
                throw new MeetingScheduleException("Minimum meeting time should be greater than 30 minus or room is occupied");
            }
            reqTimeSlot.addRoom(room);
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            reqTimeSlot.setEmployee(employee.get());
            timeSlotResponse = new TimeSlotResponse(reqTimeSlot.getTimeSlotId(), reqTimeSlot.getDescription(), reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime());
            if (reqTeamId.isPresent()) {
                value = reqTeamId.get();
                givenTeam = teamsRepository.findById(value);
                reqTimeSlot.addTeam(givenTeam.get());
                TeamsResponse teamsResponse = new TeamsResponse(value, givenTeam.get().getTeamCount(), givenTeam.get().getTeamName());
                timeSlotResponse.setTeams(List.of(teamsResponse));
            } else {
                List<Teams> teams = employee.get().getTeams();
                List<TeamsResponse> teamsResponses = teams.stream().map(team -> {
                    reqTimeSlot.addTeam(team);
                    return new TeamsResponse(team.getTeamId(), team.getTeamCount(), team.getTeamName());
                }).collect(Collectors.toList());
            }
            timeSlotRepository.save(reqTimeSlot);
            RoomsResponse roomsResponse = new RoomsResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
            timeSlotResponse.setRooms(List.of(roomsResponse));
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
        return timeSlotResponse;
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
        TimeSlotResponse timeSlotResponse = null;
        try {

            TimeSlot reqTimeSlot = new TimeSlot(collaborationRequest.getLocalDate(), collaborationRequest.getStartTime(), collaborationRequest.getEndTime(), collaborationRequest.getDescription());
            Room room = roomRepository.findByroomName(roomName);
            if (!reqTimeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(reqTimeSlot.getStartTime()) || (!roomAvailable(room, reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime()))) {
                throw new MeetingScheduleException("Minimum meeting time should be greater than 30 minus  or room is not available in given time");
            }

            reqTimeSlot.addRoom(room);
            Optional<Employee> theEmployee = employeeRepository.findById(employeeId);
            reqTimeSlot.setEmployee(theEmployee.get());
            Teams newTeam = new Teams("Temporary Team", collaborationRequest.getEmployeeList().size());
            newTeam.addEmployee(theEmployee.get());
            for (int i : collaborationRequest.getEmployeeList()) {
                Optional<Employee> employee = employeeRepository.findById(i);
                newTeam.addEmployee(employee.get());
            }
            teamsRepository.save(newTeam);
            reqTimeSlot.addTeam(newTeam);
            timeSlotRepository.save(reqTimeSlot);
            timeSlotResponse = new TimeSlotResponse(reqTimeSlot.getTimeSlotId(), reqTimeSlot.getDescription(), reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime());
            RoomsResponse roomsResponse = new RoomsResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
            TeamsResponse teamsResponse = new TeamsResponse(newTeam.getTeamId(), newTeam.getTeamCount(), newTeam.getTeamName());
            timeSlotResponse.setRooms(List.of(roomsResponse));
            timeSlotResponse.setTeams(List.of(teamsResponse));
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
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
        TimeSlotResponse timeSlotResponse = null;
        try {
            TimeSlot reqTimeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new MeetingScheduleException("TimeSlot Id is Invalid"));
            LocalTime meetingBookedTime = reqTimeSlot.getStartTime();
            LocalTime localTime = LocalTime.now();
            LocalDate localDate = LocalDate.now();
            if (!((localDate.isBefore(reqTimeSlot.getDate())) || (localDate.equals(reqTimeSlot.getDate()) && localTime.isBefore(meetingBookedTime.minus(30, ChronoUnit.MINUTES))))) {
                throw new MeetingScheduleException("Oops Time Passed....Can't delete the meeting at this moment");
            } else {
                timeSlotResponse = new TimeSlotResponse(reqTimeSlot.getTimeSlotId(), reqTimeSlot.getDescription(), reqTimeSlot.getDate(), reqTimeSlot.getStartTime(), reqTimeSlot.getEndTime());
                timeSlotRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
        return timeSlotResponse;
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
        try {
            TimeSlot theTimeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new MeetingScheduleException("TimeSlot Id is Invalid"));
            List<Room> room = theTimeSlot.getRooms();
            if (timeSlot.getDate() == null) {
                return updateMeetingDescription(theTimeSlot, timeSlot, timeSlotResponse);
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

            }
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
        return timeSlotResponse;
    }

    /**
     * this method update the description
     *
     * @param theTimeSlot
     * @param timeSlot
     * @param timeSlotResponse
     * @return
     */
    @Override
    public TimeSlotResponse updateMeetingDescription(TimeSlot theTimeSlot, TimeSlot timeSlot, TimeSlotResponse timeSlotResponse) {
        try {
            theTimeSlot.setDescription(timeSlot.getDescription());
            timeSlotResponse.setGetTimeSlotId(theTimeSlot.getTimeSlotId());
            timeSlotResponse.setStartTime(theTimeSlot.getStartTime());
            timeSlotResponse.setEndTime(theTimeSlot.getEndTime());
            timeSlotResponse.setLocalDate(theTimeSlot.getDate());
            timeSlotResponse.setGetDescription(timeSlot.getDescription());
            timeSlotRepository.save(theTimeSlot);
            return timeSlotResponse;
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
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
        TimeSlot timeSlot = null;
        TimeSlotResponse timeSlotResponse = null;
        try {
            Employee theEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new MeetingScheduleException("Employee Id is Invalid"));
            timeSlot = timeSlotRepository.findById(timeslotId).orElseThrow(() -> new MeetingScheduleException("TimeSlot Id is Invalid"));
            List<Teams> teams = timeSlot.getTeams();
            List<Employee> employees = teams.stream()
                    .flatMap(team -> {
                        team.setTimeSlots(null);
                        return team.getEmployees().stream();
                    })
                    .collect(Collectors.toList());
            Teams newTeam = new Teams("edited team", employees.size() + 1);
            newTeam.addEmployee(theEmployee);
            if (employees.stream().anyMatch(employee -> employee.getEmployeeId() == employeeId)) {
                throw new MeetingScheduleException("Employee is already in the meeting");
            }
            employees.forEach(newTeam::addEmployee);
            newTeam.addTimeSlot(timeSlot);
            teamsRepository.save(newTeam);
            TeamsResponse teamsResponse = new TeamsResponse(newTeam.getTeamId(), newTeam.getTeamCount(), newTeam.getTeamName());
            timeSlotResponse = new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime());
            timeSlotResponse.setTeams(List.of(teamsResponse));
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
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
        Teams newTeam = null;
        TimeSlot timeSlot = null;
        try {
            timeSlot = timeSlotRepository.findById(timeslotId).orElseThrow(() -> new MeetingScheduleException("TimeSlot Id is Invalid"));
            List<Teams> teams = timeSlot.getTeams();
            Set<Employee> reqEmployee= teams.stream()
                    .flatMap(team -> {
                        team.setTimeSlots(null);
                        return team.getEmployees().stream();
                    })
                    .collect(Collectors.toSet());
            newTeam = new Teams("edited team", reqEmployee.size() - 1);
            reqEmployee.stream().filter(employee -> employee.getEmployeeId() != employeeId).forEach(newTeam::addEmployee);
            newTeam.addTimeSlot(timeSlot);
            teamsRepository.save(newTeam);
        } catch (Exception e) {
            throw new MeetingScheduleException(e.getMessage());
        }
        TeamsResponse teamsResponse = new TeamsResponse(newTeam.getTeamId(), newTeam.getTeamCount(), newTeam.getTeamName());
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse(timeSlot.getTimeSlotId(), timeSlot.getDescription(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime());
        timeSlotResponse.setTeams(List.of(teamsResponse));
        return timeSlotResponse;
    }
}



