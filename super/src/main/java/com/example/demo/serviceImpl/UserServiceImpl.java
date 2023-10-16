package com.example.demo.serviceImpl;


import com.example.demo.constants.ErrorConstants;
import com.example.demo.constants.SuccessConstants;
import com.example.demo.entities.HelperDetails;
import com.example.demo.entities.Roles;
import com.example.demo.entities.User;
import com.example.demo.exception.HelperAppException;
import com.example.demo.repository.HelperDetailsRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    HelperDetailsRepository helperDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserCreationResponse createResidentUser(ResidentUserCreationRequest residentUserCreationRequest) {
        UserCreationResponse residentUserCreationResponse;
        try {
            if (userRepository.existsByEmail(residentUserCreationRequest.getEmail())) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_PRESENT);
            }
            String userStatus= SuccessConstants.STATUS_REQUESTED;
            residentUserCreationResponse = new UserCreationResponse();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null) {
                Optional<User> adminUser = userRepository.findByEmail(authentication.getName());
                if (adminUser.isPresent() && adminUser.get().getRoles().getName().equals(SuccessConstants.ROLE_ADMIN)) {
                    userStatus = SuccessConstants.STATUS_APPROVED;
                }
            }


            User newUser = new User(residentUserCreationRequest.getFirstName(), residentUserCreationRequest.getLastName(), residentUserCreationRequest.getDOB(), residentUserCreationRequest.getGender(), residentUserCreationRequest.getEmail(), passwordEncoder.encode(residentUserCreationRequest.getPassword()),userStatus);
            Roles newRoles = rolesRepository.findById(1).orElseThrow(() -> new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR));
            newUser.setRoles(newRoles);
            RoleResponse roleResponse = new RoleResponse();
            userRepository.save(newUser);
            roleResponse.setDescription(newRoles.getDescription());
            roleResponse.setId(newRoles.getId());
            roleResponse.setName(newRoles.getName());
            residentUserCreationResponse.setRole((List.of(roleResponse)));
            residentUserCreationResponse.setId(newUser.getId());
            residentUserCreationResponse.setStatus(newUser.getStatus());
            residentUserCreationResponse.setDOB(newUser.getDOB());
            residentUserCreationResponse.setEmail(newUser.getEmail());
            residentUserCreationResponse.setGender(newUser.getGender());
            residentUserCreationResponse.setFirstName(newUser.getFirstName());
            residentUserCreationResponse.setLastName(newUser.getLastName());
            residentUserCreationResponse.setPassword(residentUserCreationRequest.getPassword());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return residentUserCreationResponse;
    }


    public UserCreationResponse createHelperUser(HelperUserCreationRequest helperUserCreationRequest) {
        UserCreationResponse userCreationResponse;
        try {
            if (userRepository.existsByEmail(helperUserCreationRequest.getEmail())) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_PRESENT);
            }
            String userStatus= SuccessConstants.STATUS_REQUESTED;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null) {
                Optional<User> adminUser = userRepository.findByEmail(authentication.getName());
                if (adminUser.isPresent() && adminUser.get().getRoles().getName().equals(SuccessConstants.ROLE_ADMIN)) {
                    userStatus = SuccessConstants.STATUS_APPROVED;
                }
            }

            User newUser = new User(helperUserCreationRequest.getFirstName(), helperUserCreationRequest.getLastName(), helperUserCreationRequest.getDOB(), helperUserCreationRequest.getGender(), helperUserCreationRequest.getEmail(), passwordEncoder.encode(helperUserCreationRequest.getPassword()), userStatus);
            userCreationResponse = new UserCreationResponse();

            userCreationResponse.setDOB(newUser.getDOB());
            userCreationResponse.setEmail(newUser.getEmail());
            userCreationResponse.setGender(newUser.getGender());
            Roles newRoles = rolesRepository.findById(2).orElseThrow(() -> new HelperAppException(ErrorConstants.ROLE_NOT_FOUND));
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setName(newRoles.getName());
            roleResponse.setDescription(newRoles.getDescription());
            roleResponse.setId(newRoles.getId());
            HelperDetails helperDetails = new HelperDetails(helperUserCreationRequest.getHelperdetails().get(0).getPhonenumber(), helperUserCreationRequest.getHelperdetails().get(0).getSkill(), helperUserCreationRequest.getHelperdetails().get(0).getStatus());
            userRepository.save(newUser);
            helperDetails.setUser(newUser);
            newUser.setRoles(newRoles);
            helperDetailsRepository.save(helperDetails);

            userCreationResponse.setId(newUser.getId());
            userCreationResponse.setRole(List.of(roleResponse));
            userCreationResponse.setFirstName(newUser.getFirstName());
            userCreationResponse.setLastName(newUser.getLastName());
            userCreationResponse.setPassword(newUser.getPassword());
            userCreationResponse.setStatus(newUser.getStatus());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return userCreationResponse;
    }
}
