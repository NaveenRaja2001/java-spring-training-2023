package com.example.demo;

import com.example.demo.entities.Appointments;
import com.example.demo.entities.Slots;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class SuperApplication {
	@Autowired
	SlotRepository slotRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SuperApplication.class, args);
	}

	@Bean
	CommandLineRunner run() {
		return args -> {
//			Slots slots1=new Slots(LocalTime.parse("10:00:00"),LocalTime.parse("11:00:00"));
//			Slots slots2=new Slots(LocalTime.parse("11:00:00"),LocalTime.parse("12:00:00"));
//			Slots slots3=new Slots(LocalTime.parse("12:00:00"),LocalTime.parse("13:00:00"));
//			Slots slots4=new Slots(LocalTime.parse("14:00:00"),LocalTime.parse("15:00:00"));
//			Slots slots5=new Slots(LocalTime.parse("15:00:00"),LocalTime.parse("16:00:00"));
//			slotRepository.save(slots1);
//			slotRepository.save(slots2);
//			slotRepository.save(slots3);
//			slotRepository.save(slots4);slotRepository.save(slots5);


//			Appointments appointments=new Appointments();
//			appointments.setSlots(slotRepository.findById(1).orElseThrow());
//			appointments.setResident(userRepository.findById(6).orElseThrow());
//			appointments.setHelperId(10);
//			appointments.setLocalDate(LocalDate.parse("2019-12-27"));
//			appointmentRepository.save(appointments);
		};
	}


}
