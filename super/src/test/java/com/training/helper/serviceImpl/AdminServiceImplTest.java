package com.training.helper.serviceImpl;

import com.training.helper.entities.*;
import com.training.helper.entities.HelperDetails;
import com.training.helper.repository.AppointmentRepository;
import com.training.helper.repository.HelperDetailsRepository;
import com.training.helper.repository.RolesRepository;
import com.training.helper.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test for Admin Service
 * @Author Naveen Raja
 */
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    UserRepository userRepository;

    @Mock
    HelperDetailsRepository helperDetailsRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    RolesRepository rolesRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    /**
     * Test method to approve User
     */
    @Test
    void approveUser() {

        User newUser=new User("Naveen","N","12.01.2001","male","naveen@gmail.com","pass","requested");
        newUser.setId(1);
        Roles roles=new Roles(2,"HELPER","helper");
        newUser.setRoles(roles);

        UserRegistrationResponse expectedResponse=new UserRegistrationResponse();
        expectedResponse.setId(1);


//        RoleResponse roleResponse = new RoleResponse();
//        roleResponse.setName("HELPER");
//        roleResponse.setDescription("helper");
//        roleResponse.setId(2);

        expectedResponse.setRole(com.training.helper.constants.Roles.HELPER.getValue());
        expectedResponse.setFirstName("Naveen");
        expectedResponse.setLastName("N");
        expectedResponse.setStatus("approved");

        when(userRepository.findById(1)).thenReturn(Optional.of(newUser));
        assertEquals(adminService.approveUser(1),expectedResponse);

    }

    /**
     * Test method to retrieve all requested User
     */
    @Test
    void getAllRequestedUser() {
        User userOne=new User("Naveen","N","12.01.2001","male","naveen@gmail.com","pass","requested");
        userOne.setId(1);
        Roles roles=new Roles(2,"HELPER","helper");
        userOne.setRoles(roles);
        User userTwo=new User("Sakthi","S","12.01.2001","male","sakthi@gmail.com","pass","requested");
        userTwo.setId(2);
        userTwo.setRoles(roles);

        UserCreationResponse userOneResponse=new UserCreationResponse();
        userOneResponse.setId(1);
        userOneResponse.setDOB("12.01.2001");
        userOneResponse.setEmail("naveen@gmail.com");
        userOneResponse.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        userOneResponse.setRole(List.of(roleResponse));
        userOneResponse.setFirstName("Naveen");
        userOneResponse.setLastName("N");
        userOneResponse.setStatus("requested");

        UserCreationResponse userTwoResponse=new UserCreationResponse();
        userTwoResponse.setId(2);
        userTwoResponse.setDOB("12.01.2001");
        userTwoResponse.setEmail("sakthi@gmail.com");
        userTwoResponse.setGender("male");

        userTwoResponse.setRole(List.of(roleResponse));
        userTwoResponse.setFirstName("Sakthi");
        userTwoResponse.setLastName("S");
        userTwoResponse.setStatus("requested");

        when(userRepository.getAllRequestedUser()).thenReturn(List.of(userOne,userTwo));
        assertEquals(adminService.getAllRequestedUser(),List.of(userOneResponse,userTwoResponse));
    }

    /**
     * Test method to delete Users
     */
    @Test
    void deleteUsers() {
        UserCreationResponse deleteResponse = new UserCreationResponse();
        deleteResponse.setId(1);
        deleteResponse.setDOB("19.10.2001");
        deleteResponse.setEmail("naveen@gmail.com");
        deleteResponse.setGender("male");
//        deleteResponse.setPassword("pass");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        deleteResponse.setRole(List.of(roleResponse));
        deleteResponse.setFirstName("Naveen");
        deleteResponse.setLastName("N");
        deleteResponse.setStatus("approved");

        User newUser=new User(1,"Naveen","N","19.10.2001","male","naveen@gmail.com","pass","approved");
        Roles newRoles=new Roles();
        newRoles.setName("HELPER");
        newRoles.setDescription("helper");
        newRoles.setId(2);
        newUser.setRoles(newRoles);
        newUser.setRoles(newRoles);
        when(userRepository.findById(1)).thenReturn(Optional.of(newUser));
        HelperDetails helperDetails=new HelperDetails();
        helperDetails.setUser(newUser);
        helperDetails.setSkill("cook");
        helperDetails.setStatus("active");
        helperDetails.setPhoneNumber(90868769L);
        helperDetails.setId(2);
        when(helperDetailsRepository.findByUser_id(1)).thenReturn(Optional.of(helperDetails));
        Appointments appointment=new Appointments();
        appointment.setResident(newUser);
        appointment.setHelperId(1);
        appointment.setId(1);
        appointment.setSlots(new Slots(1, LocalTime.parse("14:00"),LocalTime.parse("15:00")));
        appointment.setLocalDate(LocalDate.parse("2018-12-21"));
        when(appointmentRepository.findAllByHelperId(1)).thenReturn(List.of(appointment));
        assertEquals(adminService.deleteUsers(1),deleteResponse);
    }

    /**
     *  Test method for updating helper
     */
    @Test
    void updateHelper() {
        Roles role = new Roles(2, "HELPER", "helper");
        User newUser=new User("Naveen","N","12.01.2001","male","naveen@gmail.com","pass","requested");
        newUser.setId(1);
        newUser.setRoles(role);
        UserCreationResponse response = new UserCreationResponse();
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");
        response.setId(1);

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
        helperUserCreationRequest.setId(1);
        helperUserCreationRequest.setLastName("N");
        helperUserCreationRequest.setDOB("12.01.2001");
        helperUserCreationRequest.setGender("male");
        helperUserCreationRequest.setEmail("naveen@gmail.com");

        org.openapitools.model.HelperDetails helperDetails = new org.openapitools.model.HelperDetails();
        helperDetails.setSkill("plumber");
        helperDetails.setPhonenumber(808764563L);
        helperDetails.setStatus("active");
        helperUserCreationRequest.setHelperdetails(List.of(helperDetails));
        when(userRepository.findById(1)).thenReturn(Optional.of(newUser));
        when(rolesRepository.findById(2)).thenReturn(Optional.of(role));
        assertEquals(adminService.updateHelper(helperUserCreationRequest),response);
    }

    /**
     *  Test method for updating resident
     */
    @Test
    void updateResident() {
        Roles role = new Roles(1, "RESIDENT", "resident");
        User newUser=new User("Naveen","N","12.01.2001","male","naveen@gmail.com","pass","requested");
        newUser.setId(1);
        newUser.setRoles(role);
        UserCreationResponse response = new UserCreationResponse();
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");
        response.setId(1);

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("RESIDENT");
        roleResponse.setDescription("resident");
        roleResponse.setId(1);

        response.setRole(List.of(roleResponse));
        response.setFirstName("Naveen");
        response.setLastName("N");
//        response.setPassword("pass");
        response.setStatus("requested");


        ResidentUserCreationRequest residentUserCreationRequest = new ResidentUserCreationRequest();
        residentUserCreationRequest.setDOB("12.01.2001");
        residentUserCreationRequest.setId(1);
        residentUserCreationRequest.setEmail("naveen@gmail.com");
        residentUserCreationRequest.setGender("male");

        residentUserCreationRequest.setFirstName("Naveen");
        residentUserCreationRequest.setLastName("N");
        residentUserCreationRequest.setPassword("pass");

        when(userRepository.findById(1)).thenReturn(Optional.of(newUser));
        when(rolesRepository.findById(1)).thenReturn(Optional.of(role));
        assertEquals(adminService.updateResident(residentUserCreationRequest),response);

    }
}