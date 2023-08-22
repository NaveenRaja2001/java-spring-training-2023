package com.example.meetingscheduler.responseObject;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.Teams;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotResponse {
   private int getTimeSlotId;
    private  String getDescription;
    private LocalDate localDate;
    private LocalTime startTime;

    private LocalTime endTime;

    public List<RoomsResponse> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomsResponse> rooms) {
        this.rooms = rooms;
    }

    private List<RoomsResponse> rooms;

    private List<TeamsResponse> teams;

    public List<TeamsResponse> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamsResponse> teams) {
        this.teams = teams;
    }

public TimeSlotResponse(){}


    public TimeSlotResponse(int getTimeSlotId, String getDescription, LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        this.getTimeSlotId = getTimeSlotId;
        this.getDescription = getDescription;
        this.localDate = localDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getGetTimeSlotId() {
        return getTimeSlotId;
    }

    public void setGetTimeSlotId(int getTimeSlotId) {
        this.getTimeSlotId = getTimeSlotId;
    }

    public String getGetDescription() {
        return getDescription;
    }

    public void setGetDescription(String getDescription) {
        this.getDescription = getDescription;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
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



    @Override
    public String toString() {
        return "TimeSlotResponse{" +
                "getTimeSlotId=" + getTimeSlotId +
                ", getDescription='" + getDescription + '\'' +
                ", localDate=" + localDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
