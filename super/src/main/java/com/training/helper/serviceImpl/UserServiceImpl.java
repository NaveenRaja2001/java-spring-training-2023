package com.training.helper.serviceImpl;


import com.training.helper.config.JwtService;
import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.ErrorConstants;
import com.training.helper.entities.HelperDetails;
import com.training.helper.entities.Roles;
import com.training.helper.entities.TokenExpired;
import com.training.helper.entities.User;
import com.training.helper.exception.HelperAppException;
import com.training.helper.repository.HelperDetailsRepository;
import com.training.helper.repository.RolesRepository;
import com.training.helper.repository.TokenExpiredRepository;
import com.training.helper.repository.UserRepository;
import com.training.helper.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.*;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
    private final TokenExpiredRepository tokenExpiredRepository;


    /**
     * This method is to create a resident directly by admin or created requested by resident to admin
     *
     * @return UserCreationResponse
     */
    public UserRegistrationResponse createResidentUser(UserRegistrationRequest residentUserCreationRequest) {
        UserRegistrationResponse residentUserCreationResponse;
        try {
            if (userRepository.existsByEmail(residentUserCreationRequest.getEmail())) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_PRESENT);
            }
            String userStatus = CommonConstants.STATUS_REQUESTED;
            residentUserCreationResponse = new UserRegistrationResponse();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Optional<User> adminUser = userRepository.findByEmail(authentication.getName());
                if (adminUser.isPresent() && adminUser.get().getRoles().getName().equals(com.training.helper.constants.Roles.ADMIN.getValue())) {
                    userStatus = CommonConstants.STATUS_APPROVED;
                }
            }
            User newUser = new User(residentUserCreationRequest.getFirstName(), residentUserCreationRequest.getLastName(), residentUserCreationRequest.getDOB(), residentUserCreationRequest.getGender(), residentUserCreationRequest.getEmail(), passwordEncoder.encode(residentUserCreationRequest.getPassword()), userStatus);
            Roles newRoles = rolesRepository.findById(1).orElseThrow(() -> new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR));
            newUser.setRoles(newRoles);
            userRepository.save(newUser);
            residentUserCreationResponse.setRole(newRoles.getName());
            residentUserCreationResponse.setId(newUser.getId());
            residentUserCreationResponse.setStatus(newUser.getStatus());
            residentUserCreationResponse.setFirstName(newUser.getFirstName());
            residentUserCreationResponse.setLastName(newUser.getLastName());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return residentUserCreationResponse;
    }

    /**
     * This method is to create a user directly by admin or created requested by helper to admin
     *
     * @return UserCreationResponse
     */
    public UserRegistrationResponse createHelperUser(UserRegistrationRequest helperUserCreationRequest) {
        UserRegistrationResponse userCreationResponse;
        try {
            if (userRepository.existsByEmail(helperUserCreationRequest.getEmail())) {
                throw new HelperAppException(ErrorConstants.USER_ALREADY_PRESENT);
            }
            String userStatus = CommonConstants.STATUS_REQUESTED;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Optional<User> adminUser = userRepository.findByEmail(authentication.getName());
                if (adminUser.isPresent() && adminUser.get().getRoles().getName().equals(com.training.helper.constants.Roles.ADMIN.getValue())) {
                    userStatus = CommonConstants.STATUS_APPROVED;
                }
            }
            User newUser = new User(helperUserCreationRequest.getFirstName(), helperUserCreationRequest.getLastName(), helperUserCreationRequest.getDOB(), helperUserCreationRequest.getGender(), helperUserCreationRequest.getEmail(), passwordEncoder.encode(helperUserCreationRequest.getPassword()), userStatus);
            userCreationResponse = new UserRegistrationResponse();
            //TODO
            Roles newRoles = rolesRepository.findById(2).orElseThrow(() -> new HelperAppException(ErrorConstants.ROLE_NOT_FOUND));
            HelperDetails helperDetails = new HelperDetails(helperUserCreationRequest.getHelperdetails().get(0).getPhonenumber(), helperUserCreationRequest.getHelperdetails().get(0).getSkill(), helperUserCreationRequest.getHelperdetails().get(0).getStatus());
            userRepository.save(newUser);
            helperDetails.setUser(newUser);
            newUser.setRoles(newRoles);
            helperDetailsRepository.save(helperDetails);

            userCreationResponse.setId(newUser.getId());
            userCreationResponse.setRole(newRoles.getName());
            userCreationResponse.setFirstName(newUser.getFirstName());
            userCreationResponse.setLastName(newUser.getLastName());
            userCreationResponse.setStatus(newUser.getStatus());
        } catch (HelperAppException e) {
            throw new HelperAppException(e.getMessage());
        }
        return userCreationResponse;
    }

    /**
     * This method is used to login if the credentials are correct
     *
     * @return AuthenticationResponse
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
            if (CommonConstants.STATUS_REQUESTED.equals(user.getStatus()) || user.getStatus().equals(CommonConstants.STATUS_REJECTED)) {
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
            var jwtAccessToken = jwtService.genToken(user, authorities);
            authenticationResponse.setEmail(user.getEmail());
            authenticationResponse.setAccessToken(jwtAccessToken);
            authenticationResponse.setMessage(CommonConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
        } catch (RuntimeException e) {
            authenticationResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            throw new HelperAppException("Authentication failed: " + e.getMessage());
        }
        return authenticationResponse;
    }

    /**
     * This method is used to log Out User
     *
     * @param httpServletRequest
     * @return LogOutResponse
     */
    @Override
    public LogOutResponse logoutUser(HttpServletRequest httpServletRequest) {
        LogOutResponse logOutResponse = new LogOutResponse();
        String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring("Bearer ".length());
                TokenExpired tokenExpired = new TokenExpired();
                tokenExpired.setToken(token);
                tokenExpiredRepository.save(tokenExpired);
                logOutResponse.setMessage("Token has expired");
            } catch (HelperAppException e) {
                throw new HelperAppException(e);
            }
        }
        return logOutResponse;
    }
}

