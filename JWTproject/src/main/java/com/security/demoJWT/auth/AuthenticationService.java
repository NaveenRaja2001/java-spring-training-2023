package com.security.demoJWT.auth;

import com.security.demoJWT.config.JwtService;
import com.security.demoJWT.user.Role;
import com.security.demoJWT.user.User;
import com.security.demoJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;

private final JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest request) {
       var user = User.builder()
               .firstName(request.getFirstname())
               .lastName(request.getLastname())
               .gender(request.getGender())
               .DOB(request.getDOB())
               .email(request.getEmail())
               .password(passwordEncoder.encode(request.getPassword()))
               .role(Role.USER)
               .build();

       userRepository.save(user);
       var jwtToken=jwtService.generateToken(user);
       return AuthenticationResponse.builder()
               .token(jwtToken)
               .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
