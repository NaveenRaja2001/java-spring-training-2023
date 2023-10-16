package com.example.demo.controller;


import com.example.demo.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;


    @Test
    void createHelperUser() {
        UserCreationResponse response=new UserCreationResponse();
        response.setId(1);
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
        response.setPassword("pass");
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
        when(userService.createHelperUser(helperUserCreationRequest)).thenReturn(response);
        assertEquals(userService.createHelperUser(helperUserCreationRequest),response);

    }

    @Test
    void createResidentUser() {
        UserCreationResponse response=new UserCreationResponse();
        response.setId(1);
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("RESIDENT");
        roleResponse.setDescription("resident");
        roleResponse.setId(2);

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
        when(userService.createResidentUser(residentUserCreationRequest)).thenReturn(response);
        assertEquals(userService.createResidentUser(residentUserCreationRequest),response);
    }
}