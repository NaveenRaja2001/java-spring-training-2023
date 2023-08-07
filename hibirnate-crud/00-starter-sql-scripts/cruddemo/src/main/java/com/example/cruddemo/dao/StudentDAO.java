package com.example.cruddemo.dao;

import com.example.cruddemo.entity.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student theStudent);
    Student read(int id);
    List<Student> findAll(String lastName);


    void update(Student student);

    void delete(Integer id);
int removeAll();
}
