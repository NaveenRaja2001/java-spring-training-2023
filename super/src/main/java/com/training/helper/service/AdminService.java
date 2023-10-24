package com.training.helper.service;

import org.openapitools.model.HelperUserCreationRequest;
import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.UserCreationResponse;
import org.openapitools.model.UserRegistrationResponse;

import java.util.List;

public interface AdminService {
    UserRegistrationResponse approveUser(Integer userId);

    List<UserCreationResponse> getAllRequestedUser();

    UserCreationResponse deleteUsers(Integer userId);

    HelperUserCreationRequest updateHelper(HelperUserCreationRequest helperUserCreationRequest);

    ResidentUserCreationRequest updateResident(ResidentUserCreationRequest residentUserCreationRequest);

    UserRegistrationResponse rejectUsers(Integer id);
}
