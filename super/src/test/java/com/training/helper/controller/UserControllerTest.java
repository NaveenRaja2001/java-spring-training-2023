package com.training.helper.controller;


import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.Roles;
import com.training.helper.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;


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
        residentUserCreationRequest.setDOB("12.01.2001");
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

    }
}