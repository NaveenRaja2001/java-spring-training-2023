package com.training.helper.service;

import jakarta.servlet.http.HttpServletRequest;
import org.openapitools.model.*;

public interface UserService {
    UserRegistrationResponse createResidentUser(UserRegistrationRequest residentUserCreationRequest);

    UserRegistrationResponse createHelperUser(UserRegistrationRequest helperUserCreationRequest);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    LogOutResponse logoutUser(HttpServletRequest httpServletRequest);
}
