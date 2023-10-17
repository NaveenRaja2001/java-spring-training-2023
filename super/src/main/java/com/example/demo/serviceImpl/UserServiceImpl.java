package com.example.demo.serviceImpl;


import com.example.demo.config.JwtService;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AuthenticationRequest;
import org.openapitools.model.AuthenticationResponse;
import org.openapitools.model.HelperUserCreationRequest;
import org.openapitools.model.ResidentUserCreationRequest;
import org.openapitools.model.RoleResponse;
import org.openapitools.model.UserCreationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service class that handles User-related endpoints
 *
 * @Author Naveen Raja
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final HelperDetailsRepository helperDetailsRepository;


    /**
     * This method is to create a resident directly by admin or created requested by resident to admin
     *
     * @return UserCreationResponse
     */
    public UserCreationResponse createResidentUser(ResidentUserCreationRequest residentUserCreationRequest) {
        UserCreationResponse residentUserCreationResponse;
        try{
            if (userRepository.existsByEmail(residentUserCreationRequest.getEmail())) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_PRESENT);
            }
            String userStatus = SuccessConstants.STATUS_REQUESTED;
            residentUserCreationResponse = new UserCreationResponse();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Optional<User> adminUser = userRepository.findByEmail(authentication.getName());
                if (adminUser.isPresent() && adminUser.get().getRoles().getName().equals(SuccessConstants.ROLE_ADMIN)) {
                    userStatus = SuccessConstants.STATUS_APPROVED;
                }
            }
            User newUser = new User(residentUserCreationRequest.getFirstName(), residentUserCreationRequest.getLastName(), residentUserCreationRequest.getDOB(), residentUserCreationRequest.getGender(), residentUserCreationRequest.getEmail(), passwordEncoder.encode(residentUserCreationRequest.getPassword()), userStatus);
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

    /**
     * This method is to create a helper directly by admin or created requested by helper to admin
     *
     * @return UserCreationResponse
     */
    public UserCreationResponse createHelperUser(HelperUserCreationRequest helperUserCreationRequest) {
        UserCreationResponse userCreationResponse;
        try {
            if (userRepository.existsByEmail(helperUserCreationRequest.getEmail())) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_PRESENT);
            }
            String userStatus = SuccessConstants.STATUS_REQUESTED;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
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
            userCreationResponse.setPassword(helperUserCreationRequest.getPassword());
            userCreationResponse.setStatus(newUser.getStatus());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return userCreationResponse;
    }

    /**
     * This method is uded to login if the credentials are correct
     *
     * @return AuthenticationResponse
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if (SuccessConstants.STATUS_REQUESTED.equals(user.getStatus())) {
                throw new HelperAppException(ErrorConstants.USER_NOT_APPROVED_MESSAGE);
            }
            List<Roles> role = null;
            if (user != null) {
                role = List.of(user.getRoles());
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            role.stream().forEach(c -> {
                authorities.add(new SimpleGrantedAuthority(c.getName()));
            });
            var jwtAccessToken = jwtService.genTok(user, authorities);
            authenticationResponse.setEmail(user.getEmail());
            authenticationResponse.setAccessToken(jwtAccessToken);
            authenticationResponse.setMessage(SuccessConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
        } catch (RuntimeException e) {
            authenticationResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            throw new HelperAppException("Authentication failed: " + e.getMessage());
        }
        return authenticationResponse;
    }
}
