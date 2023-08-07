package com.example.demorestapi.service;

import com.example.demorestapi.dao.EmployeeRepository;
import com.example.demorestapi.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
   private EmployeeRepository employeeRepository;
@Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> employee = employeeRepository.findById(theId);
        Employee theEmployee=null;
        if(employee.isPresent()){
            theEmployee=employee.get();
        }
        else{
            throw new RuntimeException("Did not find the employeeId"+theId);
        }
        return theEmployee;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int theId) {
    employeeRepository.deleteById(theId);
    }
}
