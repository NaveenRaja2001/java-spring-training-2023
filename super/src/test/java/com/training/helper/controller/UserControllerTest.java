package com.training.helper.controller;


import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.Roles;
import com.training.helper.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.AuthenticationRequest;
import org.openapitools.model.AuthenticationResponse;
import org.openapitools.model.LogOutResponse;
import org.openapitools.model.UserRegistrationRequest;
import org.openapitools.model.UserRegistrationResponse;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test for User Controller
 *
 * @Author Naveen Raja
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    @Mock
    HttpServletRequest httpServletRequest;

    /**
     * Test for creating the resident
     */
    @Test
    void createResidentUser() {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(1);

        response.setRole(Roles.RESIDENT.getValue());
        response.setFirstName("Naveen");
        response.setLastName("N");
        response.setStatus("requested");

        UserRegistrationRequest residentUserCreationRequest = new UserRegistrationRequest();
        residentUserCreationRequest.setDob("12.01.2001");
        residentUserCreationRequest.setEmail("naveen@gmail.com");
        residentUserCreationRequest.setGender("male");

        residentUserCreationRequest.setFirstName("Naveen");
        residentUserCreationRequest.setLastName("N");
        residentUserCreationRequest.setPassword("pass");
        when(userService.createResidentUser(residentUserCreationRequest)).thenReturn(response);
        assertEquals(userService.createResidentUser(residentUserCreationRequest), response);

    }

    /**
     * Test for login the user
     */
    @Test
    void loginUser() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("naveen@gmail.com");
        authenticationRequest.setPassword("pass");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail("naveen@gmail.com");
        authenticationResponse.setMessage(CommonConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
        authenticationResponse.setAccessToken("token");
        when(userService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);
        assertEquals(userService.authenticate(authenticationRequest), authenticationResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = userController.loginUser(authenticationRequest);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(authenticationResponse);
    }

    @Test
    void createUser() {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(1);

        response.setRole(Roles.RESIDENT.getValue());
        response.setFirstName("Naveen");
        response.setLastName("N");
        response.setStatus("requested");

        UserRegistrationRequest residentUserCreationRequest = new UserRegistrationRequest();
        residentUserCreationRequest.setDob("12.01.2001");
        residentUserCreationRequest.setEmail("naveen@gmail.com");
        residentUserCreationRequest.setGender("male");
        residentUserCreationRequest.setRole(UserRegistrationRequest.RoleEnum.RESIDENT);

        residentUserCreationRequest.setFirstName("Naveen");
        residentUserCreationRequest.setLastName("N");
        residentUserCreationRequest.setPassword("pass");
        when(userService.createResidentUser(residentUserCreationRequest)).thenReturn(response);
        assertEquals(userService.createResidentUser(residentUserCreationRequest), response);


        ResponseEntity<UserRegistrationResponse> responseEntity = userController.createUser(residentUserCreationRequest);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(201);
        assertThat(responseEntity.getBody()).isEqualTo(response);
    }

    @Test
    void logoutUser() {
        LogOutResponse logOutResponse = new LogOutResponse();
        logOutResponse.setMessage("Token has expired");
        when(userService.logoutUser(httpServletRequest)).thenReturn(logOutResponse);
        assertEquals(userService.logoutUser(httpServletRequest),logOutResponse);

        ResponseEntity<LogOutResponse> responseEntity = userController.logoutUser();
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(logOutResponse);
    }
}