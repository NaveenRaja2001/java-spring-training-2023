package com.training.helper.serviceImpl;

import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.ErrorConstants;
import com.training.helper.entities.Appointments;
import com.training.helper.entities.HelperDetails;
import com.training.helper.entities.Roles;
import com.training.helper.entities.User;
import com.training.helper.exception.HelperAppException;
import com.training.helper.repository.AppointmentRepository;
import com.training.helper.repository.HelperDetailsRepository;
import com.training.helper.repository.RolesRepository;
import com.training.helper.repository.UserRepository;
import com.training.helper.service.AdminService;
import jakarta.validation.ConstraintViolationException;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles Admin-related endpoints
 *
 * @Author Naveen Raja
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
   private HelperDetailsRepository helperDetailsRepository;
    @Autowired
   private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method is used to approve the requested User
     *
     * @param userId
     * @return UserCreationResponse
     */
    @Override
    public UserRegistrationResponse approveUser(Integer userId) {
        UserRegistrationResponse userCreationResponse = new UserRegistrationResponse();
        try {
            User requestedUser = userRepository.findById(userId).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if (!requestedUser.getStatus().equals(CommonConstants.STATUS_REQUESTED)) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_ACTIVE_OR_REJECTED);
            }
            requestedUser.setStatus(CommonConstants.STATUS_APPROVED);

            userCreationResponse.setId(requestedUser.getId());
            Roles newRoles = requestedUser.getRoles();
//            RoleResponse roleResponse = new RoleResponse();
//            roleResponse.setName(newRoles.getName());
//            roleResponse.setDescription(newRoles.getDescription());
//            roleResponse.setId(newRoles.getId());

            userCreationResponse.setRole(requestedUser.getRoles().getName());
            userCreationResponse.setFirstName(requestedUser.getFirstName());
            userCreationResponse.setLastName(requestedUser.getLastName());
            userCreationResponse.setStatus(requestedUser.getStatus());
            userRepository.save(requestedUser);
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return userCreationResponse;
    }

    /**
     * This method is used to retrieve all the requested User
     *
     * @return List of UserCreationResponse
     */
    @Override
    public List<UserCreationResponse> getAllRequestedUser() {
        List<UserCreationResponse> requestedUserResponse = null;
        try {
            List<User> requestedUsers = userRepository.findAllByStatus(CommonConstants.STATUS_REQUESTED);
            requestedUserResponse = requestedUsers.stream()
                    .map(user -> {
                        UserCreationResponse userCreationResponse = new UserCreationResponse();
                        userCreationResponse.setId(user.getId());
                        userCreationResponse.setDOB(user.getDOB());
                        userCreationResponse.setEmail(user.getEmail());
                        userCreationResponse.setGender(user.getGender());

                        Roles newRoles = user.getRoles();
                        RoleResponse roleResponse = new RoleResponse();
                        roleResponse.setName(newRoles.getName());
                        roleResponse.setDescription(newRoles.getDescription());
                        roleResponse.setId(newRoles.getId());

                        userCreationResponse.setRole(List.of(roleResponse));
                        userCreationResponse.setFirstName(user.getFirstName());
                        userCreationResponse.setLastName(user.getLastName());
//                        userCreationResponse.setPassword(user.getPassword());
                        userCreationResponse.setStatus(user.getStatus());

                        return userCreationResponse;
                    })
                    .collect(Collectors.toList());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return requestedUserResponse;
    }

    /**
     * This method is used to delete Users
     *
     * @param userId
     * @return UserCreationResponse
     */
    @Override
    public UserCreationResponse deleteUsers(Integer userId) {
        UserCreationResponse deleteResponse = new UserCreationResponse();
        try {
            Boolean isResident = false;
            List<Appointments> appointments = new ArrayList<>();
            User requestedUser = userRepository.findById(userId).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if (requestedUser.getRoles().getName().equals(com.training.helper.constants.Roles.HELPER.getValue())) {
                HelperDetails helperDetails = helperDetailsRepository.findByUser_id(userId).orElseThrow(() -> new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR));
                helperDetailsRepository.delete(helperDetails);
                appointments = appointmentRepository.findAllByHelperId(userId);

            } else {
                appointments = appointmentRepository.findAllByResident_id(userId);
                isResident = true;
            }
            appointmentRepository.deleteAll(appointments);
            if (isResident) {
                userRepository.delete(requestedUser);
            }
            deleteResponse.setId(requestedUser.getId());
            deleteResponse.setDOB(requestedUser.getDOB());
            deleteResponse.setEmail(requestedUser.getEmail());
            deleteResponse.setGender(requestedUser.getGender());

            Roles newRoles = requestedUser.getRoles();
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setName(newRoles.getName());
            roleResponse.setDescription(newRoles.getDescription());
            roleResponse.setId(newRoles.getId());

            deleteResponse.setRole(List.of(roleResponse));
            deleteResponse.setFirstName(requestedUser.getFirstName());
            deleteResponse.setLastName(requestedUser.getLastName());
            deleteResponse.setStatus(requestedUser.getStatus());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return deleteResponse;
    }

    /**
     * This method is used to update helper
     *
     * @param helperUserCreationRequest
     * @return UserCreationResponse
     */
    @Override
    public UserCreationResponse updateHelper(HelperUserCreationRequest helperUserCreationRequest) {
        UserCreationResponse helperUpdateResponse;
        try {
            User requestedUser = userRepository.findById(helperUserCreationRequest.getId()).orElseThrow(() -> new HelperAppException(ErrorConstants.HELPER_NOT_FOUND_ERROR));
            if (!requestedUser.getRoles().getName().equals(com.training.helper.constants.Roles.HELPER.getValue())) {
                throw new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR);
            }
            helperUpdateResponse = new UserCreationResponse();

            requestedUser.setId(helperUserCreationRequest.getId());
            requestedUser.setFirstName(helperUserCreationRequest.getFirstName());
            requestedUser.setLastName(helperUserCreationRequest.getLastName());
            requestedUser.setGender(helperUserCreationRequest.getGender());
            requestedUser.setDOB(helperUserCreationRequest.getDOB());
            requestedUser.setEmail(helperUserCreationRequest.getEmail());
            requestedUser.setPassword(passwordEncoder.encode(helperUserCreationRequest.getPassword()));
            requestedUser.setStatus(requestedUser.getStatus());
            RoleResponse roleResponse = new RoleResponse();
            requestedUser.setRoles(rolesRepository.findById(2).orElseThrow(() -> new HelperAppException(ErrorConstants.ROLE_NOT_FOUND)));
            HelperDetails helperDetails = new HelperDetails(helperUserCreationRequest.getHelperdetails().get(0).getId(), helperUserCreationRequest.getHelperdetails().get(0).getPhonenumber(), helperUserCreationRequest.getHelperdetails().get(0).getSkill(), helperUserCreationRequest.getHelperdetails().get(0).getStatus());
            userRepository.save(requestedUser);
            helperDetails.setUser(requestedUser);
            helperDetailsRepository.save(helperDetails);

            roleResponse.setDescription(requestedUser.getRoles().getDescription());
            roleResponse.setId(requestedUser.getRoles().getId());
            roleResponse.setName(requestedUser.getRoles().getName());
            helperUpdateResponse.setRole((List.of(roleResponse)));
            helperUpdateResponse.setId(requestedUser.getId());
            helperUpdateResponse.setStatus(requestedUser.getStatus());
            helperUpdateResponse.setDOB(requestedUser.getDOB());
            helperUpdateResponse.setEmail(requestedUser.getEmail());
            helperUpdateResponse.setGender(requestedUser.getGender());
            helperUpdateResponse.setFirstName(requestedUser.getFirstName());
            helperUpdateResponse.setLastName(requestedUser.getLastName());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        catch (ConstraintViolationException e){
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
        return helperUpdateResponse;
    }

    /**
     * This method to update resident
     *
     * @param residentUserCreationRequest
     * @return UserCreationResponse
     */
    @Override
    public UserCreationResponse updateResident(ResidentUserCreationRequest residentUserCreationRequest) {
        UserCreationResponse residentUpdateResponse;
        try {
            User requestedUser = userRepository.findById(residentUserCreationRequest.getId()).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if (!requestedUser.getRoles().getName().equals(com.training.helper.constants.Roles.RESIDENT.getValue())) {
                throw new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR);
            }
            residentUpdateResponse = new UserCreationResponse();
            User updatedUser = new User(residentUserCreationRequest.getId(), residentUserCreationRequest.getFirstName(), residentUserCreationRequest.getLastName(), residentUserCreationRequest.getDOB(), residentUserCreationRequest.getGender(), residentUserCreationRequest.getEmail(), passwordEncoder.encode(residentUserCreationRequest.getPassword()), requestedUser.getStatus());
            RoleResponse roleResponse = new RoleResponse();
            updatedUser.setRoles(rolesRepository.findById(1).orElseThrow(() -> new HelperAppException(ErrorConstants.ROLE_NOT_FOUND)));

            userRepository.save(updatedUser);
            roleResponse.setDescription(requestedUser.getRoles().getDescription());
            roleResponse.setId(requestedUser.getRoles().getId());
            roleResponse.setName(requestedUser.getRoles().getName());
            residentUpdateResponse.setRole((List.of(roleResponse)));
            residentUpdateResponse.setId(updatedUser.getId());
            residentUpdateResponse.setStatus(updatedUser.getStatus());
            residentUpdateResponse.setDOB(updatedUser.getDOB());
            residentUpdateResponse.setEmail(updatedUser.getEmail());
            residentUpdateResponse.setGender(updatedUser.getGender());
            residentUpdateResponse.setFirstName(updatedUser.getFirstName());
            residentUpdateResponse.setLastName(updatedUser.getLastName());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return residentUpdateResponse;
    }
    /**
     * This method is used to reject the requested User
     *
     * @param id
     * @return UserCreationResponse
     */
    @Override
    public UserRegistrationResponse rejectUsers(Integer id) {
        UserRegistrationResponse rejectResponse;
        try {
            User requestedUser = userRepository.findById(id).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if(requestedUser.getStatus().equals(CommonConstants.STATUS_APPROVED) || requestedUser.getStatus().equals(CommonConstants.STATUS_REJECTED)){
                throw new HelperAppException("User is already approved or rejected");
            }
            requestedUser.setStatus(CommonConstants.STATUS_REJECTED);

            userRepository.save(requestedUser);
            rejectResponse=new UserRegistrationResponse();
            rejectResponse.setId(requestedUser.getId());

            rejectResponse.setRole(requestedUser.getRoles().getName());
            rejectResponse.setFirstName(requestedUser.getFirstName());
            rejectResponse.setLastName(requestedUser.getLastName());
            rejectResponse.setStatus(requestedUser.getStatus());
            userRepository.save(requestedUser);
        }
        catch (HelperAppException e){
            throw new HelperAppException(e.getMessage());
        }
        return rejectResponse;
    }
}
