package com.example.demo;

import com.example.demo.dao.AppDAO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.InstructorDetail;
import com.example.demo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AdvanceMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvanceMappingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			createCourseAndStudent(appDAO);
		};


	}

	private void createCourseAndStudent(AppDAO appDAO) {
		Course tempCourse = new Course("courseTitle");
		Student student1 = new Student("sdfsdfsdfsd", "N", "@gmail");
		Student student2 = new Student("sdfssdfasdasdfsdfsd", "asdas", "@gmil");

		tempCourse.addStudent(student1);
		tempCourse.addStudent(student2);
		appDAO.update(tempCourse);

//	private void updateCourse(AppDAO appDAO) {
//		Course course=appDAO.findById(10);
//		course.setTitle("Navee");
//		appDAO.update(course);
//	}
//
//	private void updateInstructor(AppDAO appDAO) {
//		Instructor instructor=appDAO.findByInstructorId(2);
//		instructor.setEmail("sss");
//		appDAO.update(instructor);
//	}
//
//	private void findCoursesByInstructor(AppDAO appDAO) {
//		Instructor instructor=appDAO.findByInstructorId(2);
//		System.out.println(instructor);
//		List<Course> courses=appDAO.findCoursesByIntructor(2);
//		instructor.setCourse(courses);
//		System.out.print(instructor.getCourse());
//	}
//
//	private void findInstructorWithCourses(AppDAO appDAO) {
//		Instructor instructor=appDAO.findByInstructorId(2);
//		System.out.println(instructor);
//		System.out.print(instructor.getCourse());
//
//	}
//
//	private void createInstructorWithCourses(AppDAO appDAO) {
//		Instructor tempInstructor=new Instructor("Naveen","Raja","naveen@gamil.com");
//		InstructorDetail instructorDetail =new InstructorDetail("/tech/kinggggggg","fun");
//		tempInstructor.setInstructorDetail(instructorDetail);
//		Course course2=new Course("SDWED");
//		Course course1=new Course("SDEFDWED");
//		Course course3=new Course("SDWEDRR");
//		tempInstructor.add(course1);
//		tempInstructor.add(course2);
//		tempInstructor.add(course3);
//		System.out.println(tempInstructor);
//		System.out.println(tempInstructor.getCourse());
//		appDAO.save(tempInstructor);
//	}
//
//	private void deleteInstructorDetailsById(AppDAO appDAO) {
//		appDAO.deleteInstructorDetailById(3);
//	}
//
//	private void findInstructorDetails(AppDAO appDAO) {
//		InstructorDetail instructorDetail=appDAO.findInstructorDetailById(1);
//		System.out.println(instructorDetail);
//		System.out.println(instructorDetail.getInstructor());
//	}
//
//	private void createInstructor(AppDAO appDAO) {
//		Instructor tempInstructor=new Instructor("Naveen","Raja","naveen@gamil.com");
//		InstructorDetail instructorDetail =new InstructorDetail("/tech/kinggggggg","fun");
//		tempInstructor.setInstructorDetail(instructorDetail);
//           appDAO.save(tempInstructor);
//	}
//
//
//	private void findInstructor(AppDAO appDAO) {
//  int id=1;
// Instructor instructor= appDAO.findByInstructorId(id);
// System.out.println(instructor);
//	}
	}
}
