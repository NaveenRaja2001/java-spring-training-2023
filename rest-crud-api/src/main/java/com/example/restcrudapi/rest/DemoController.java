package com.example.restcrudapi.rest;

import com.example.restcrudapi.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/test")
public class DemoController {

    private List<Student> theStudents;
    @PostConstruct
    public void loadData(){
        theStudents=new ArrayList<>();
        theStudents.add(new Student("Naveen","Raja"));
        theStudents.add(new Student("Biida","hario"));
        theStudents.add(new Student("hai","Raja"));

    }
    @GetMapping("/api")
    public List<Student> getStudents(){
        return theStudents;
    }

    @GetMapping("/api/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        if(studentId<0 || studentId>=theStudents.size()){
            throw new StudentNotFoundException("Student id not found" +studentId);

        }
        return theStudents.get(studentId);
    }


}
