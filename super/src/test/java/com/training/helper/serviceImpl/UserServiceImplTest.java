package com.training.helper.serviceImpl;

import com.training.helper.config.JwtService;
import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.ErrorConstants;
import com.training.helper.entities.Roles;
import com.training.helper.entities.User;
import com.training.helper.exception.HelperAppException;
import com.training.helper.repository.HelperDetailsRepository;
import com.training.helper.repository.RolesRepository;
import com.training.helper.repository.TokenExpiredRepository;
import com.training.helper.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
    HttpServletRequest httpServletRequest;

    @Mock
    HelperDetailsRepository helperDetailsRepository;

    @Mock
    TokenExpiredRepository tokenExpiredRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    /**
     * Test for creating resident
     */
    @Test
    void createResidentUser() {
        UserRegistrationResponse response = new UserRegistrationResponse();

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("RESIDENT");
        roleResponse.setDescription("resident");
        roleResponse.setId(1);

        response.setRole(com.training.helper.constants.Roles.RESIDENT.getValue());
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
        UserRegistrationResponse response = new UserRegistrationResponse();

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        response.setRole(com.training.helper.constants.Roles.HELPER.getValue());
        response.setFirstName("Naveen");
        response.setLastName("N");
        response.setStatus("requested");

        UserRegistrationRequest helperUserCreationRequest = new UserRegistrationRequest();
        helperUserCreationRequest.setFirstName("Naveen");
        helperUserCreationRequest.setLastName("N");
        helperUserCreationRequest.setDob("12.01.2001");
        helperUserCreationRequest.setGender("male");
        helperUserCreationRequest.setEmail("naveen@gmail.com");

        HelperDetails helperDetails = new HelperDetails();
        helperDetails.setSkill("plumber");
        helperDetails.setPhoneNumber(808764563L);
        helperDetails.setStatus("active");
        helperUserCreationRequest.setHelperDetails(List.of(helperDetails));
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
        authenticationResponse.setMessage(CommonConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
        authenticationResponse.setAccessToken("token");
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(newUser));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("HELPER"));
        when(jwtService.genToken(newUser, authorities)).thenReturn("token");
        assertEquals(userService.authenticate(authenticationRequest), authenticationResponse);
    }


    /**
     * Test for logOut User
     */
    @Test
    void logoutUser() {
        LogOutResponse logOutResponse = new LogOutResponse();
        logOutResponse.setMessage("Token has expired");
        when(httpServletRequest.getHeader(AUTHORIZATION)).thenReturn("Bearer token");

        assertEquals(userService.logoutUser(httpServletRequest),logOutResponse);
    }
}