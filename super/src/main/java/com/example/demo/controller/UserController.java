package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.serviceImpl.AuthenticationService;

import org.openapitools.api.RegistrationApi;
import org.openapitools.model.AuthenticationResponse;
import org.openapitools.model.HelperUserCreationRequest;

import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(org.openapitools.model.AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return service.authenticate(request);
//    }

}
