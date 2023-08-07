package com.example.cruddemo;

import com.example.cruddemo.dao.StudentDAO;
import com.example.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}
@Bean
public CommandLineRunner commandLineRunner(StudentDAO studentDAO){
	return runner->{
//			createStudent(studentDAO);

			createMultipleStudent(studentDAO);

//          readStudent(studentDAO);

//		findAllStudent(studentDAO);

//		updateStudent(studentDAO);
//		removeStudent(studentDAO);
//		removeAllStudent(studentDAO);
	};
}

	private void removeAllStudent(StudentDAO studentDAO) {
		studentDAO.removeAll();
	}

	private void removeStudent(StudentDAO studentDAO) {
		studentDAO.delete(3);
	}

	private void updateStudent(StudentDAO studentDAO) {
		Student student=studentDAO.read(1);
		student.setFirstName("Naveen");
		studentDAO.update(student);

	}

	private void findAllStudent(StudentDAO studentDAO) {
		List<Student> students=studentDAO.findAll("B");
		for(Student student:students){
			System.out.println(student);
		}
	}

	private void readStudent(StudentDAO studentDAO) {
		Student obj=new Student("Kamalash","H","kamalash@gmail.com");
		studentDAO.save(obj);
		int getId= obj.getId();
		System.out.println(studentDAO.read(getId));

	}

	private void createMultipleStudent(StudentDAO studentDAO) {
		System.out.println("Creating multiple user");
		Student student1=new Student("Sathi","R","123@gmail.com");
		Student student2=new Student("Hari","S","hari@gmail.com");
		Student student3=new Student("Bidda","B","bidda3@gmail.com");
		studentDAO.save(student1);
		studentDAO.save(student2);
		studentDAO.save(student3);
	}

	private void createStudent(StudentDAO studentDAO) {
		System.out.println("Creating new Student obj");
		Student student=new Student("Naveen","Raja","naveenraja19102001@gmail.com");
studentDAO.save(student);
System.out.println("Saved Student .Generated Id: "+student.getId());

	}

}
