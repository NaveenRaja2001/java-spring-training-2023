package com.example.demo.controller;

import com.example.demo.service.AdminService;
import org.openapitools.api.AdminApi;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController implements AdminApi {

    @Autowired
    AdminService adminService;

    @Override
    public ResponseEntity<UserCreationResponse> approveUser(Integer userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.approveUser(userId));
    }

    @Override
    public ResponseEntity<List<UserCreationResponse>> getAllRequestedUser() {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.getAllRequestedUser());
    }


}
