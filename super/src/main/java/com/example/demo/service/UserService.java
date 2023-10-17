package com.example.demo.service;

import org.openapitools.model.*;

public interface UserService {
    UserCreationResponse createResidentUser(ResidentUserCreationRequest residentUserCreationRequest);

    UserCreationResponse createHelperUser(HelperUserCreationRequest helperUserCreationRequest);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
