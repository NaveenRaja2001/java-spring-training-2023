package com.training.meetingscheduler.requestobject;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CollaborationRequest {
    private int timeSlotId;
    private  String description;
    private LocalDate localDate;
    private LocalTime startTime;

    private LocalTime endTime;

    private List<Integer> employeeList;

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Integer> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Integer> employeeList) {
        this.employeeList = employeeList;
    }

    public CollaborationRequest(int timeSlotId, String description, LocalDate localDate, LocalTime startTime, LocalTime endTime, List<Integer> employeeList) {
        this.timeSlotId = timeSlotId;
        this.description = description;
        this.localDate = localDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "CollaborationRequest{" +
                "timeSlotId=" + timeSlotId +
                ", description='" + description + '\'' +
                ", localDate=" + localDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", employeeList=" + employeeList +
                '}';
    }
}
