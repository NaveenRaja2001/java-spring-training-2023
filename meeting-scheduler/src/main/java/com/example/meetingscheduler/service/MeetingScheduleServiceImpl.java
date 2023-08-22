package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.repository.EmployeeRepository;
import com.example.meetingscheduler.repository.RoomRepository;
import com.example.meetingscheduler.repository.TeamsRepository;
import com.example.meetingscheduler.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @Override
    public String addMeeting(TimeSlot timeSlot, int employeeId, String roomName, Optional<Integer> teamid) {
        //meeting timing should  be minimum 30 minutes
        if (!timeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(timeSlot.getStartTime())) {
            throw new IllegalArgumentException("Minimum meeting time should be greater than 30 minus");
        }
        Room room = roomRepository.findByroomName(roomName);
        Teams givenTeam = null;
        timeSlot.addRoom(room);
        Employee employee = employeeRepository.findById(employeeId);
        timeSlot.setEmployee(employee);
        if (teamid.isPresent()) {
            int value = teamid.get();
            givenTeam = teamsRepository.findById(value);
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
            timeSlotRepository.save(timeSlot);
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

    @Override
    public String createNewTeam(List<Integer> employees, TimeSlot timeSlot, int employeeId, String roomName) {
        Room room = roomRepository.findByroomName(roomName);
        if (!timeSlot.getEndTime().minus(30, ChronoUnit.MINUTES).isAfter(timeSlot.getStartTime()) || (!roomAvailable(room, timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime()))) {
            return "Minimum meeting time should be greater than 30 minus  or room is not available in given time";
        }

        timeSlot.addRoom(room);
        Employee theEmployee = employeeRepository.findById(employeeId);
        timeSlot.setEmployee(theEmployee);
        Teams newTeam = new Teams("Temporary Team", employees.size());
        newTeam.addEmployee(theEmployee);
        for (int i : employees) {
            Employee employee = employeeRepository.findById(i);

            newTeam.addEmployee(employee);
        }
        teamsRepository.save(newTeam);
        timeSlot.addTeam(newTeam);
        timeSlotRepository.save(timeSlot);
        return "New team is and meeting is scheduled successfully";
    }

    @Override
    public String deleteMeeting(int id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id);
        LocalTime meetingBookedTime = timeSlot.getStartTime();
        LocalTime localTime = LocalTime.now();
        LocalDate localDate=LocalDate.now();
        if (localDate.equals(timeSlot.getDate())&&localTime.isBefore(meetingBookedTime.minus(30, ChronoUnit.MINUTES))) {
            timeSlotRepository.deleteById(id);
            return "TimeSlot is deleted successfully";
        } else {
            return "Can't delete the meeting at this moment";
        }
    }

    @Override
    public String updateMeeting(TimeSlot timeSlot, int id) {
        TimeSlot theTimeSlot = timeSlotRepository.findById(id);
        List<Room> room = theTimeSlot.getRooms();
        if (!roomAvailable(room.get(0), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime())) {
            return "This Room is already booked in this timing";
        } else {
            theTimeSlot.setStartTime(timeSlot.getStartTime());
            theTimeSlot.setEndTime(timeSlot.getEndTime());
            timeSlotRepository.save(theTimeSlot);
            return "meeting timing is updated";
        }
    }

    @Override
    public String updateMeetingDescription(String description, int timeslotId) {

        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId);
        timeSlot.setDescription(description);
        timeSlotRepository.save(timeSlot);
        return "Description updated Successfully";
    }

    @Override
    public String updateMeetingAddEmployee(int timeslotId, int employeeid) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId);
        Employee theEmployee = employeeRepository.findById(employeeid);

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
        teamsRepository.save(newTeam);
        return "Employee has been added to the new meeting";
    }

    @Override
    public String updateMeetingRemoveEmployee(int timeslotId, int employeeid) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId);
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
        teamsRepository.save(newTeam);
        return "Employee has been removed from the meeting";
    }
}



