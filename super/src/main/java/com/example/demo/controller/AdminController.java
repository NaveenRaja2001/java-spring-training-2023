package com.example.demo.controller;

import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import org.openapitools.api.AdminApi;
import org.openapitools.model.HelperUserCreationRequest;
import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class that handles Admin-related endpoints
 *
 * @Author Naveen Raja
 */
@RestController
public class AdminController implements AdminApi {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    /**
     * This endpoint is used to approve the requested User
     *
     * @param userId
     * @return UserCreationResponse
     */
    @Override
    public ResponseEntity<UserCreationResponse> approveUser(Integer userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.approveUser(userId));
    }

    /**
     * This endpoint used to add helper directly by the admin
     *
     * @param helperUserCreationRequest
     * @return UserCreationResponse
     */
    @Override
    public ResponseEntity<UserCreationResponse> createHelperAndApprove(HelperUserCreationRequest helperUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createHelperUser(helperUserCreationRequest));
    }

    /**
     * This endpoint used to add resident directly by the admin
     *
     * @param residentUserCreationRequest
     * @return UserCreationResponse
     */
    @Override
    public ResponseEntity<UserCreationResponse> createResidentAndApprove(ResidentUserCreationRequest residentUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createResidentUser(residentUserCreationRequest));
    }

    /**
     * This endpoint is used to retrieve all requested User
     *
     * @return List<UserCreationResponse>
     */
    @Override
    public ResponseEntity<List<UserCreationResponse>> getAllRequestedUser() {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.getAllRequestedUser());
    }

    @Override
    public ResponseEntity<UserCreationResponse> updateHelper(HelperUserCreationRequest helperUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.updateHelper(helperUserCreationRequest));
    }

    @Override
    public ResponseEntity<UserCreationResponse> updateResident(ResidentUserCreationRequest residentUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.updateResident(residentUserCreationRequest));
    }

    @Override
    public ResponseEntity<UserCreationResponse> deleteUsers(Integer userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.deleteUsers(userId));
    }


}
