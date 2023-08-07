package com.example.demorestapi.dao;

import com.example.demorestapi.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{
    private EntityManager entityManager;
@Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> theQuery=entityManager.createQuery("from Employee", Employee.class);

        List<Employee> employees=theQuery.getResultList();
        return employees;
    }

    @Override
    public Employee FindById(int theId) {
    Employee employee=entityManager.find(Employee.class,theId);
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
    Employee dbemployee=entityManager.merge(employee);
        return dbemployee;
    }

    @Override
    public void deleteById(int theId) {
        Employee employee=entityManager.find(Employee.class,theId);
        entityManager.remove(employee);
    }
}
