package com.example.meetingscheduler.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="teams")
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;
    @Column(name="team_name")
    private String teamName;
    @Column(name="team_count")
    private int teamCount;


@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
@JoinTable(name = "employee_teams",
joinColumns =@JoinColumn(name ="team_id"),
inverseJoinColumns =@JoinColumn(name = "employee_id"))
    private List<Employee> employees;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teams_timeslots",
            joinColumns =@JoinColumn(name ="team_id"),
            inverseJoinColumns =@JoinColumn(name = "time_slot_id"))
    private List<TimeSlot> timeSlots;
    public  void addTimeSlot(TimeSlot timeSlot){
        if(timeSlots==null){
            timeSlots=new ArrayList<>();
        }
        timeSlots.add(timeSlot);
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public List<Employee> getEmployees() {
        return employees;
    }


    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Teams(){}

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public Teams(String teamName, int teamCount) {
        this.teamName = teamName;
        this.teamCount = teamCount;
    }

    @Override
    public String toString() {
        return "Teams{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamCount=" + teamCount +
                '}';
    }
    public  void addEmployee(Employee theEmployee){
        if(employees==null){
            employees=new ArrayList<>();
        }
        employees.add(theEmployee);
    }


}
