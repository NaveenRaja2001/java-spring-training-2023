package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findById(int id);

    void update(Employee employee1);
}
