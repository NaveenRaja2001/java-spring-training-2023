package com.example.demo.service;

import org.openapitools.model.HelperUserCreationRequest;
import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    UserCreationResponse approveUser(Integer userId);

    List<UserCreationResponse> getAllRequestedUser();

    UserCreationResponse deleteUsers(Integer userId);

    UserCreationResponse updateHelper(HelperUserCreationRequest helperUserCreationRequest);

    UserCreationResponse updateResident(ResidentUserCreationRequest residentUserCreationRequest);
}
