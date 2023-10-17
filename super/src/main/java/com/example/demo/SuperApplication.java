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

		};
	}


}
