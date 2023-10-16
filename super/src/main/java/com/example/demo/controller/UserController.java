package com.example.demo.controller;

import com.example.demo.config.AuthenticationRequest;
import com.example.demo.service.UserService;
import com.example.demo.serviceImpl.AuthenticationService;

import com.example.demo.serviceImpl.UserServiceImpl;
import org.openapitools.api.RegistrationApi;
import org.openapitools.model.HelperUserCreationRequest;

import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController implements RegistrationApi {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService service;

    @Override
    public ResponseEntity<UserCreationResponse> createHelperUser(HelperUserCreationRequest helperUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createHelperUser(helperUserCreationRequest));
    }

    @Override
    public ResponseEntity<UserCreationResponse> createResidentUser(ResidentUserCreationRequest residentUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createResidentUser(residentUserCreationRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return service.authenticate(request);
    }

}
