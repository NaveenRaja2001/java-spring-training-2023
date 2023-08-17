package com.example.meetingscheduler.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
////Ignoring new fields on JSON objects
//@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "time_slot")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private int timeSlotId;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "teams_timeslots",
            joinColumns =@JoinColumn(name ="time_slot_id"),
            inverseJoinColumns =@JoinColumn(name = "team_id"))
    private List<Teams> teams;
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "timeslots_room",
            joinColumns =@JoinColumn(name ="time_slot_id"),
            inverseJoinColumns =@JoinColumn(name = "room_id"))
    private List<Room> rooms;
    public  void addRoom(Room room){
        if(rooms==null){
            rooms=new ArrayList<>();
        }
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Teams> getTeams() {
        return teams;
    }
    public  void addTeam(Teams team){
        if(teams==null){
            teams=new ArrayList<>();
        }
        teams.add(team);
    }
    public void setTeams(List<Teams> teams) {
        this.teams = teams;
    }

    public TimeSlot(){}

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TimeSlot(LocalDate date, LocalTime start_time, LocalTime end_Time, String description) {
        this.date = date;
        this.startTime = start_time;
        this.endTime = end_Time;
        this.description = description;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "timeSlotId=" + timeSlotId +
                ", date='" + date + '\'' +
                ", start_time='" + startTime + '\'' +
                ", end_Time='" + endTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
