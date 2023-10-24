package com.training.helper;

import com.training.helper.repository.AppointmentRepository;
import com.training.helper.repository.RolesRepository;
import com.training.helper.repository.SlotRepository;
import com.training.helper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class SuperApplication {
    @Autowired
    SlotRepository slotRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SuperApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
//			User admin=new User("Naveen","N","19/10/2001", UserRegistrationRequest.GenderEnum.MALE.getValue(), "naveen@gmail.com",passwordEncoder.encode("password"), CommonConstants.STATUS_APPROVED);
//       	Roles roles=rolesRepository.findById(3).orElseThrow();
//			userRepository.save(admin);
        };
    }


}
