package com.training.meetingscheduler.serviceinterface;

import com.training.meetingscheduler.entity.Employee;

public interface EmployeeService {
    Employee findById(int id);

    void update(Employee employee1);
}
