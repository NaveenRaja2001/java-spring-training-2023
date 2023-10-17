package com.example.demo.serviceImpl;

import com.example.demo.config.JwtService;
import com.example.demo.constants.SuccessConstants;
import com.example.demo.entities.Roles;
import com.example.demo.entities.User;
import com.example.demo.repository.HelperDetailsRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test for User Service class
 *
 * @Author Naveen Raja
 */
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

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    /**
     * Test for creating resident
     */
    @Test
    void createResidentUser() {
        UserCreationResponse response = new UserCreationResponse();
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


        ResidentUserCreationRequest residentUserCreationRequest = new ResidentUserCreationRequest();
        residentUserCreationRequest.setDOB("12.01.2001");
        residentUserCreationRequest.setEmail("naveen@gmail.com");
        residentUserCreationRequest.setGender("male");

        residentUserCreationRequest.setFirstName("Naveen");
        residentUserCreationRequest.setLastName("N");
        residentUserCreationRequest.setPassword("pass");
        Roles role = new Roles(1, "RESIDENT", "resident");
        when(userRepository.existsByEmail(residentUserCreationRequest.getEmail())).thenReturn(false);
        when(rolesRepository.findById(1)).thenReturn(Optional.of(role));
        assertEquals(userService.createResidentUser(residentUserCreationRequest), response);
    }

    /**
     * Test for creating helper
     */
    @Test
    void createHelperUser() {
        UserCreationResponse response = new UserCreationResponse();
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

        HelperUserCreationRequest helperUserCreationRequest = new HelperUserCreationRequest();
        helperUserCreationRequest.setFirstName("Naveen");
        helperUserCreationRequest.setLastName("N");
        helperUserCreationRequest.setDOB("12.01.2001");
        helperUserCreationRequest.setGender("male");
        helperUserCreationRequest.setEmail("naveen@gmail.com");

        HelperDetails helperDetails = new HelperDetails();
        helperDetails.setSkill("plumber");
        helperDetails.setPhonenumber(808764563L);
        helperDetails.setStatus("active");
        helperUserCreationRequest.setHelperdetails(List.of(helperDetails));
        Roles role = new Roles(2, "HELPER", "helper");
        when(userRepository.existsByEmail(helperUserCreationRequest.getEmail())).thenReturn(false);
        when(rolesRepository.findById(2)).thenReturn(Optional.of(role));
        assertEquals(userService.createHelperUser(helperUserCreationRequest), response);
    }

    /**
     * Test for authenticate User
     */
    @Test
    void authenticate() {
        User newUser = new User(1, "Naveen", "n", "12.01.2001", "male", "naveen@gmail.com", "pass", "approved");
        newUser.setRoles(new Roles(2, "HELPER", "helper"));
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("naveen@gmail.com");
        authenticationRequest.setPassword("pass");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail("naveen@gmail.com");
        authenticationResponse.setMessage(SuccessConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
        authenticationResponse.setAccessToken("token");
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(newUser));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("HELPER"));
        when(jwtService.genTok(newUser, authorities)).thenReturn("token");
        assertEquals(userService.authenticate(authenticationRequest), authenticationResponse);
    }
}