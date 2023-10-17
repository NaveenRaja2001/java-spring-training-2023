package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.serviceImpl.AuthenticationService;

import org.openapitools.api.UserApi;
import org.openapitools.model.AuthenticationResponse;
import org.openapitools.model.HelperUserCreationRequest;

import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles users-related endpoints
 *
 * @Author Naveen Raja
 */

@RestController
public class UserController implements UserApi {

    @Autowired
    UserService userService;


    /**
     * Endpoints to register helper for approval
     *
     * @return UserCreationResponse
     */
    @Override
    public ResponseEntity<UserCreationResponse> createHelperUser(HelperUserCreationRequest helperUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createHelperUser(helperUserCreationRequest));
    }

    /**
     * Endpoints to register resident for approval
     *
     * @return UserCreationResponse
     */
    @Override
    public ResponseEntity<UserCreationResponse> createResidentUser(ResidentUserCreationRequest residentUserCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createResidentUser(residentUserCreationRequest));
    }

    /**
     * Endpoints to login user
     *
     * @param authenticationRequest Authenticate a user with email and password (required)
     * @return AuthenticationResponse
     */
    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(org.openapitools.model.AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return service.authenticate(request);
//    }

}
