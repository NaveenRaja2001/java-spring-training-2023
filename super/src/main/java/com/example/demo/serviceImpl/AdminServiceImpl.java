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
import com.example.demo.service.AdminService;
import org.openapitools.model.HelperUserCreationRequest;
import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.RoleResponse;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
   private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    HelperDetailsRepository helperDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method is used to approve the requested User
     * @param userId
     * @return UserCreationResponse
     */
    @Override
    public UserCreationResponse approveUser(Integer userId) {
        UserCreationResponse userCreationResponse = new UserCreationResponse();
        try{
            User requestedUser=userRepository.findById(userId).orElseThrow(()-> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if(requestedUser.getStatus().equals(SuccessConstants.STATUS_APPROVED)){
                throw new HelperAppException(ErrorConstants.USER_ALREADY_ACTIVE);
            }
            requestedUser.setStatus(SuccessConstants.STATUS_APPROVED);

            userCreationResponse.setId(requestedUser.getId());
            userCreationResponse.setDOB(requestedUser.getDOB());
            userCreationResponse.setEmail(requestedUser.getEmail());
            userCreationResponse.setGender(requestedUser.getGender());

            Roles newRoles = requestedUser.getRoles();
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setName(newRoles.getName());
            roleResponse.setDescription(newRoles.getDescription());
            roleResponse.setId(newRoles.getId());

            userCreationResponse.setRole(List.of(roleResponse));
            userCreationResponse.setFirstName(requestedUser.getFirstName());
            userCreationResponse.setLastName(requestedUser.getLastName());
            userCreationResponse.setPassword(requestedUser.getPassword());
            userCreationResponse.setStatus(requestedUser.getStatus());
            userRepository.save(requestedUser);
        }

        catch (HelperAppException e){
            throw new HelperAppException(e.getMessage());
        }
        return userCreationResponse;
    }

    /**
     *  This method is used to retrieve all the requested User
     * @return List of UserCreationResponse
     */
    @Override
    public List<UserCreationResponse> getAllRequestedUser() {
        List<UserCreationResponse> requestedUserResponse = null;
        try{
            List<User> requestedUsers=userRepository.getAllRequestedUser();
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
                        userCreationResponse.setPassword(user.getPassword());
                        userCreationResponse.setStatus(user.getStatus());

                        return userCreationResponse;
                    })
                    .collect(Collectors.toList());
        }
        catch (HelperAppException e){
            throw new HelperAppException(e.getMessage());
        }
        return requestedUserResponse;
    }

    @Override
    public UserCreationResponse deleteUsers(Integer userId) {
        UserCreationResponse deleteResponse = new UserCreationResponse();
        try{
            User requestedUser=userRepository.findById(userId).orElseThrow(()-> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if(requestedUser.getRoles().getName().equals(SuccessConstants.ROLE_HELPER)){
                HelperDetails helperDetails=helperDetailsRepository.findByUser_id(userId).orElseThrow(()-> new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR));
                helperDetailsRepository.delete(helperDetails);
            }
            userRepository.delete(requestedUser);
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
            deleteResponse.setPassword(requestedUser.getPassword());
            deleteResponse.setStatus(requestedUser.getStatus());
        }
        catch (HelperAppException e){
            throw new HelperAppException(e.getMessage());
        }
        return deleteResponse;
    }

    @Override
    public UserCreationResponse updateHelper(HelperUserCreationRequest helperUserCreationRequest) {
        UserCreationResponse helperUpdateResponse;
        try {
            User requestedUser=userRepository.findById(helperUserCreationRequest.getId()).orElseThrow(() -> new HelperAppException(ErrorConstants.HELPER_NOT_FOUND_ERROR));
            if (!requestedUser.getRoles().getName().equals(SuccessConstants.ROLE_HELPER)) {
                throw new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR);
            }
            helperUpdateResponse = new UserCreationResponse();

            User updatedUser = new User(helperUserCreationRequest.getId(), helperUserCreationRequest.getFirstName(), helperUserCreationRequest.getLastName(), helperUserCreationRequest.getDOB(), helperUserCreationRequest.getGender(), helperUserCreationRequest.getEmail(),passwordEncoder.encode(helperUserCreationRequest.getPassword()), SuccessConstants.STATUS_REQUESTED);
            RoleResponse roleResponse = new RoleResponse();
            updatedUser.setRoles(rolesRepository.findById(2).orElseThrow());
            userRepository.save(updatedUser);
            HelperDetails helperDetails = new HelperDetails(helperUserCreationRequest.getHelperdetails().get(0).getId(),helperUserCreationRequest.getHelperdetails().get(0).getPhonenumber(), helperUserCreationRequest.getHelperdetails().get(0).getSkill(), helperUserCreationRequest.getHelperdetails().get(0).getStatus());
            userRepository.save(updatedUser);
            helperDetails.setUser(updatedUser);
            helperDetailsRepository.save(helperDetails);

            roleResponse.setDescription(requestedUser.getRoles().getDescription());
            roleResponse.setId(requestedUser.getRoles().getId());
            roleResponse.setName(requestedUser.getRoles().getName());
            helperUpdateResponse.setRole((List.of(roleResponse)));
            helperUpdateResponse.setId(updatedUser.getId());
            helperUpdateResponse.setStatus(updatedUser.getStatus());
            helperUpdateResponse.setDOB(updatedUser.getDOB());
            helperUpdateResponse.setEmail(updatedUser.getEmail());
            helperUpdateResponse.setGender(updatedUser.getGender());
            helperUpdateResponse.setFirstName(updatedUser.getFirstName());
            helperUpdateResponse.setLastName(updatedUser.getLastName());
            helperUpdateResponse.setPassword(helperUserCreationRequest.getPassword());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return helperUpdateResponse;
    }

    @Override
    public UserCreationResponse updateResident(ResidentUserCreationRequest residentUserCreationRequest) {
        return null;
    }
}
