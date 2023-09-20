package com.security.demoJWT;

import com.security.demoJWT.user.Roles;
import com.security.demoJWT.user.User;
import com.security.demoJWT.user.UserRepository;
import com.security.demoJWT.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
@EnableJpaRepositories
public class DemoJwtApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoJwtApplication.class, args);
	}

@Bean
BCryptPasswordEncoder bCryptPasswordEncoder(){
	return new BCryptPasswordEncoder();
}

@Autowired
	UserRepository userRepository;

@Bean
CommandLineRunner run(UserService userService){
	return args -> {
//		Roles roles1=new Roles(null,"ROLE_USER","this is user");
//		Roles roles2=new Roles(null,"ROLE_BUSINESS_USER","this is business user");
//		Roles roles3=new Roles(null,"ROLE_ADMIN","this is admin");
//			userService.saveRole(roles1);
//			userService.saveRole(roles2);
//			userService.saveRole(roles3);
////
//		userService.saveUser(new User("Naveen","Raja","19.10.2001","male","naveenraja@gmail.com",new HashSet<>(),"pass"));
//		userService.saveUser(new User("Sakthi","S","19.10.2001","male","Sakthi@gmail.com",new HashSet<>(),"pass"));
//		userService.saveUser(new User("hari","h","19.10.2001","male","hari@gmail.com",new HashSet<>(),"pass"));
////
//			userService.addToUser("naveenraja@gmail.com","ROLE_USER");
////			userService.addToUser("Sakthi@gmail.com","ROLE_BUSINESS_USER");
////			userService.addToUser("hari@gmail.com","ROLE_ADMIN");
//			System.out.print("||||||||||||||"+userRepository.findByEmail("naveenraja@gmail.com").get().getRoles());

	};
}

}


