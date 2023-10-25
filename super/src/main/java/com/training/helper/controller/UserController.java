package com.training.helper.controller;

import com.training.helper.constants.Roles;
import com.training.helper.exception.HelperAppException;
import com.training.helper.projection.MailSender;
import com.training.helper.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.openapitools.api.UserApi;
import org.openapitools.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Controller class that handles users-related endpoints
 *
 * @Author Naveen Raja
 */

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    MailSender mailSender;


    /**
     * This endpoint is used to register user
     *
     * @param userRegistrationRequest
     * @return UserRegistrationResponse
     */
    @Override
    public ResponseEntity<UserRegistrationResponse> createUser(UserRegistrationRequest userRegistrationRequest) {

        if (userRegistrationRequest.getRole().getValue().equals(Roles.HELPER.getValue()) && userRegistrationRequest.getHelperDetails() == null) {
            throw new HelperAppException("Helper Details needed for registration");
        }
        if (userRegistrationRequest.getRole().getValue().equals(Roles.RESIDENT.getValue()) && userRegistrationRequest.getHelperDetails() != null) {
            throw new HelperAppException("Helper Details is not needed for resident registration");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userRegistrationRequest.getRole().getValue().equals(Roles.HELPER.getValue())
                        ? userService.createHelperUser(userRegistrationRequest)
                        : userService.createResidentUser(userRegistrationRequest)
        );
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

    /**
     * Endpoints to logOut User
     *
     * @return LogOutResponse
     */
    @Override
    public ResponseEntity<LogOutResponse> logoutUser() {
        return ResponseEntity.ok(userService.logoutUser(httpServletRequest));
    }

    @GetMapping("/u/u")
    public String jh(ArrayList<String> notifyUser,Integer userId){
       return mailSender.sendSimpleMail(notifyUser, userId);

    }

}
