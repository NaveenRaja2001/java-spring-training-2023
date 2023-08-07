package com.example.demorestapi.dao;

import com.example.demorestapi.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
    Employee FindById(int theId);

    Employee save(Employee employee);

    void deleteById(int theId);
}
