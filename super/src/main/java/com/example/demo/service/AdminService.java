package com.example.demo.service;

import org.openapitools.model.UserCreationResponse;

import java.util.List;

public interface AdminService {
    UserCreationResponse approveUser(Integer userId);

    List<UserCreationResponse> getAllRequestedUser();
}
