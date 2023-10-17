package com.example.demo.serviceImpl;


import com.example.demo.config.JwtService;
import com.example.demo.constants.ErrorConstants;
import com.example.demo.constants.SuccessConstants;
import com.example.demo.entities.Roles;
import com.example.demo.exception.HelperAppException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AuthenticationRequest;
import org.openapitools.model.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        try {
//            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR));
//            if (SuccessConstants.STATUS_REQUESTED.equals(user.getStatus())) {
//                throw new HelperAppException(ErrorConstants.USER_NOT_APPROVED_MESSAGE);
//            }
//            List<Roles> role = null;
//            if (user != null) {
//                role = List.of(user.getRoles());
//            }
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
////            Set<Roles> set = new HashSet<>();
//            role.stream().forEach(c -> {
////                set.add(new Roles(c.getName()));
//                authorities.add(new SimpleGrantedAuthority(c.getName()));
//            });
////            user.setRoles(set);
////            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
//            var jwtAccessToken = jwtService.genTok(user, authorities);
//            authenticationResponse.setEmail(user.getEmail());
//            authenticationResponse.setAccessToken(jwtAccessToken);
//            authenticationResponse.setMessage(SuccessConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
//        } catch (RuntimeException e) {
//            authenticationResponse.setMessage(e.getMessage());
//        } catch (Exception e) {
//            throw new HelperAppException("Authentication failed: " + e.getMessage());
//        }
//        return authenticationResponse;
//    }


//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        String authHeader = request.getHeader(AUTHORIZATION);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            try {
//                String token = authHeader.substring("Bearer ".length());
//                TokenExpired tokenExpired = new TokenExpired();
//                tokenExpired.setToken(token);
//                tokenExpiredRepository.save(tokenExpired);
//            } catch (Exception e) {
//                throw new TicketBookingException(e);
//            }
//        }
//        return ResponseEntity.ok("lOGout");
//    }
}
