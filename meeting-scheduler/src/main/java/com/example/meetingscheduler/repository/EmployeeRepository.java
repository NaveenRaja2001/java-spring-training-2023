package com.example.meetingscheduler.repository;

import com.example.meetingscheduler.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findById(int id);


}
