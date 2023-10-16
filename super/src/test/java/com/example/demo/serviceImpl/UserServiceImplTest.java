package com.example.demo.serviceImpl;

import com.example.demo.entities.Roles;
import com.example.demo.repository.HelperDetailsRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    RolesRepository rolesRepository;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    HelperDetailsRepository helperDetailsRepository;

    @Test
    void createResidentUser() {
        UserCreationResponse response=new UserCreationResponse();
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("RESIDENT");
        roleResponse.setDescription("resident");
        roleResponse.setId(1);

        response.setRole(List.of(roleResponse));
        response.setFirstName("Naveen");
        response.setLastName("N");
        response.setPassword("pass");
        response.setStatus("requested");


        ResidentUserCreationRequest residentUserCreationRequest=new ResidentUserCreationRequest();
        residentUserCreationRequest.setDOB("12.01.2001");
        residentUserCreationRequest.setEmail("naveen@gmail.com");
        residentUserCreationRequest.setGender("male");

        residentUserCreationRequest.setFirstName("Naveen");
        residentUserCreationRequest.setLastName("N");
        residentUserCreationRequest.setPassword("pass");
        Roles role=new Roles(1,"RESIDENT","resident");
       when(userRepository.existsByEmail(residentUserCreationRequest.getEmail())).thenReturn(false);
       when(rolesRepository.findById(1)).thenReturn(Optional.of(role));
       assertEquals(userService.createResidentUser(residentUserCreationRequest),response);
    }

    @Test
    void createHelperUser() {
        UserCreationResponse response=new UserCreationResponse();
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        response.setRole(List.of(roleResponse));
        response.setFirstName("Naveen");
        response.setLastName("N");
        response.setStatus("requested");

        HelperUserCreationRequest helperUserCreationRequest=new HelperUserCreationRequest();
        helperUserCreationRequest.setFirstName("Naveen");
        helperUserCreationRequest.setLastName("N");
        helperUserCreationRequest.setDOB("12.01.2001");
        helperUserCreationRequest.setGender("male");
        helperUserCreationRequest.setEmail("naveen@gmail.com");
        helperUserCreationRequest.setPassword("pass");

        HelperDetails helperDetails=new HelperDetails();
        helperDetails.setSkill("plumber");
        helperDetails.setPhonenumber(808764563L);
        helperDetails.setStatus("active");
helperUserCreationRequest.setHelperdetails(List.of(helperDetails));
        Roles role=new Roles(2,"HELPER","helper");
        when(userRepository.existsByEmail(helperUserCreationRequest.getEmail())).thenReturn(false);
        when(rolesRepository.findById(2)).thenReturn(Optional.of(role));
        assertEquals(userService.createHelperUser(helperUserCreationRequest),response);


    }
}