package com.training.meetingscheduler.service;

import com.training.meetingscheduler.entity.Employee;
import com.training.meetingscheduler.repository.EmployeeRepository;
import com.training.meetingscheduler.serviceinterface.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void update(Employee employee1) {
        employeeRepository.save(employee1);
    }
}
