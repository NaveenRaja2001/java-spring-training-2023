package com.example.demo.dao;

import com.example.demo.entity.Course;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.InstructorDetail;

import java.util.List;

public interface AppDAO {
    void save(Instructor instructor);
    Instructor findByInstructorId(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleteInstructorDetailById(int id);

    List<Course> findCoursesByIntructor(int id);

 void update(Instructor instructor);
 void update(Course course);

 Course findById(int id);

}
