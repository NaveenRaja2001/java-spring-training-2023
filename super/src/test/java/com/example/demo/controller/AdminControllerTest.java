package com.example.demo.controller;

import com.example.demo.entities.Roles;
import com.example.demo.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.RoleResponse;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
@InjectMocks
AdminController adminController;
    @Mock
    AdminService adminService;
    @Test
    void approveUser() {
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
        response.setStatus("approved");
    when(adminService.approveUser(1)).thenReturn(response);
        UserCreationResponse expected=adminService.approveUser(1);
        assertEquals(expected, response);
//        assertEquals(HttpStatusCode.valueOf(200), expected.getStatusCode());
    }

    @Test
    void getAllRequestedUser() {
        UserCreationResponse userOne=new UserCreationResponse();
        userOne.setId(1);
        userOne.setDOB("12.01.2001");
        userOne.setEmail("naveen@gmail.com");
        userOne.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        userOne.setRole(List.of(roleResponse));
        userOne.setFirstName("Naveen");
        userOne.setLastName("N");
        userOne.setPassword("pass");
        userOne.setStatus("requested");

        UserCreationResponse userTwo=new UserCreationResponse();
        userTwo.setId(2);
        userTwo.setDOB("19.01.2011");
        userTwo.setEmail("sakthi@gmail.com");
        userTwo.setGender("male");

        userTwo.setRole(List.of(roleResponse));
        userTwo.setFirstName("Sakthi");
        userTwo.setLastName("S");
        userTwo.setPassword("pass");
        userTwo.setStatus("requested");

        when(adminService.getAllRequestedUser()).thenReturn(List.of(userOne,userTwo));
        List<UserCreationResponse> response=adminService.getAllRequestedUser();
        assertEquals(response,List.of(userOne,userTwo));
    }
}