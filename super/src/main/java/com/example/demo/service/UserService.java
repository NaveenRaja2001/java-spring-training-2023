package com.example.demo.service;

import org.openapitools.model.HelperUserCreationRequest;
import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;

public interface UserService {
    UserCreationResponse createResidentUser(ResidentUserCreationRequest residentUserCreationRequest);

    UserCreationResponse createHelperUser(HelperUserCreationRequest helperUserCreationRequest);
}
