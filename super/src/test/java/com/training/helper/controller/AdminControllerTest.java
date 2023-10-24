package com.training.helper.controller;

import com.training.helper.constants.Roles;
import com.training.helper.service.AdminService;
import com.training.helper.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test for admin controller endpoints
 *
 * @Author Naveen Raja
 */
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @InjectMocks
    AdminController adminController;
    @Mock
    AdminService adminService;

    @Mock
    UserService userService;

    /**
     * Test method for approving User
     */

    @Test
    void approveUser() {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(1);

        response.setRole(Roles.HELPER.getValue());
        response.setFirstName("Naveen");
        response.setLastName("N");
//        response.setPassword("pass");
        response.setStatus("approved");
        when(adminService.approveUser(1)).thenReturn(response);
        UserRegistrationResponse expected = adminService.approveUser(1);

        ResponseEntity<UserRegistrationResponse> responseEntity = adminController.approveUser(1);
        assertEquals(expected, response);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(response);
    }

    /**
     * Test method for retrieving all requested User
     */
    @Test
    void getAllRequestedUser() {
        UserCreationResponse userOne = new UserCreationResponse();
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
        userOne.setStatus("requested");

        UserCreationResponse userTwo = new UserCreationResponse();
        userTwo.setId(2);
        userTwo.setDOB("19.01.2011");
        userTwo.setEmail("sakthi@gmail.com");
        userTwo.setGender("male");

        userTwo.setRole(List.of(roleResponse));
        userTwo.setFirstName("Sakthi");
        userTwo.setLastName("S");
//        userTwo.setPassword("pass");
        userTwo.setStatus("requested");

        when(adminService.getAllRequestedUser()).thenReturn(List.of(userOne, userTwo));
        List<UserCreationResponse> response = adminService.getAllRequestedUser();
        assertEquals(response, List.of(userOne, userTwo));

        ResponseEntity<List<UserCreationResponse>> responseEntity = adminController.getAllRequestedUser();
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(userOne, userTwo));
    }




    /**
     * Test method for deleting User
     */
    @Test
    void deleteUsers() {
        UserCreationResponse deleteResponse = new UserCreationResponse();
        deleteResponse.setId(1);
        deleteResponse.setDOB("19.10.2001");
        deleteResponse.setEmail("naveen@gmail.com");
        deleteResponse.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        deleteResponse.setRole(List.of(roleResponse));
        deleteResponse.setFirstName("Naveen");
        deleteResponse.setLastName("N");
        deleteResponse.setStatus("approved");

        when(adminService.deleteUsers(1)).thenReturn(deleteResponse);
        assertEquals(adminService.deleteUsers(1),deleteResponse);

        ResponseEntity<UserCreationResponse> responseEntity = adminController.deleteUsers(1);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);
        assertThat(responseEntity.getBody()).isEqualTo(deleteResponse);
    }

    /**
     *  Test method for updating helper
     */
    @Test
    void updateHelper() {
        HelperUserCreationRequest response = new HelperUserCreationRequest();
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");

        response.setFirstName("Naveen");
        response.setLastName("N");

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
        when(adminService.updateHelper(helperUserCreationRequest)).thenReturn(response);
        assertEquals(adminService.updateHelper(helperUserCreationRequest),response);

        ResponseEntity<HelperUserCreationRequest> responseEntity = adminController.updateHelper(helperUserCreationRequest);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(response);
    }

    /**
     *  Test method for updating resident
     */
    @Test
    void updateResident() {
        ResidentUserCreationRequest response = new ResidentUserCreationRequest();
        response.setDOB("12.01.2001");
        response.setEmail("naveen@gmail.com");
        response.setGender("male");

        response.setFirstName("Naveen");
        response.setLastName("N");

        ResidentUserCreationRequest residentUserCreationRequest = new ResidentUserCreationRequest();
        residentUserCreationRequest.setDOB("12.01.2001");
        residentUserCreationRequest.setEmail("naveen@gmail.com");
        residentUserCreationRequest.setGender("male");

        residentUserCreationRequest.setFirstName("Naveen");
        residentUserCreationRequest.setLastName("N");
        residentUserCreationRequest.setPassword("pass");
        when(adminService.updateResident(residentUserCreationRequest)).thenReturn(response);
        assertEquals(adminService.updateResident(residentUserCreationRequest),response);

        ResponseEntity<ResidentUserCreationRequest> responseEntity = adminController.updateResident(residentUserCreationRequest);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(response);
    }


    /**
     *  Test method for rejecting resident
     */
    @Test
    void rejectUsers() {
        UserRegistrationResponse deleteResponse = new UserRegistrationResponse();
        deleteResponse.setId(1);

        deleteResponse.setRole("HELPER");
        deleteResponse.setFirstName("Naveen");
        deleteResponse.setLastName("N");
        deleteResponse.setStatus("requested");

        when(adminService.rejectUsers(1)).thenReturn(deleteResponse);
        assertEquals(adminService.rejectUsers(1),deleteResponse);

        ResponseEntity<UserRegistrationResponse> responseEntity = adminController.rejectUsers(1);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(deleteResponse);
    }


}