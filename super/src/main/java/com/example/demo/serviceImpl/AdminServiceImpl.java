package com.example.demo.serviceImpl;

import com.example.demo.entities.Roles;
import com.example.demo.entities.User;
import com.example.demo.exception.HelperAppException;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import org.openapitools.model.RoleResponse;
import org.openapitools.model.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
   private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Override
    public UserCreationResponse approveUser(Integer userId) {
        UserCreationResponse userCreationResponse = new UserCreationResponse();
        try{
            User requestedUser=userRepository.findById(userId).orElseThrow(()-> new HelperAppException("User Id not found"));
            if(requestedUser.getStatus().equals("approved")){
                throw new HelperAppException("user is already active");
            }
            requestedUser.setStatus("approved");

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

        catch (Exception e){
            throw new HelperAppException(e.getMessage());
        }
        return userCreationResponse;
    }

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
        catch (Exception e){
            throw new HelperAppException(e.getMessage());
        }
        return requestedUserResponse;
    }
}
