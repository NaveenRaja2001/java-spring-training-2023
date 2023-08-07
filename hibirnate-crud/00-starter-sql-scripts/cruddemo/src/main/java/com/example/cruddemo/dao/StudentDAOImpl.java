package com.example.cruddemo.dao;

import com.example.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{
private EntityManager entityManager;
@Autowired
    StudentDAOImpl(EntityManager theEntityManager){
        this.entityManager=theEntityManager;
    }

    @Override
    @Transactional
    public void save(Student theStudent) {
entityManager.persist(theStudent);
    }

    @Override
    public Student read(int id) {
       return entityManager.find(Student.class,id);
    }

    @Override
    public List<Student> findAll(String lastName) {
        TypedQuery<Student> theQuery=entityManager.createQuery("FROM Student where lastName=:thedata",Student.class);
        theQuery.setParameter("thedata",lastName);
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
    Student myStudent=entityManager.find(Student.class,id);
        entityManager.remove(myStudent);
    }

    @Override
    @Transactional
    public int removeAll() {
       int removeLine=entityManager.createQuery("DELETE FROM Student").executeUpdate();
       return removeLine;
    }
}
