package com.example.meetingscheduler.service;

import com.example.meetingscheduler.entity.Employee;
import com.example.meetingscheduler.repository.EmployeeRepository;
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
