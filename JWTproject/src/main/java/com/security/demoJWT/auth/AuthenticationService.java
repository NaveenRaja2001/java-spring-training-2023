package com.security.demoJWT.auth;

import com.security.demoJWT.config.JwtService;
import com.security.demoJWT.config.RoleCustomRepo;
import com.security.demoJWT.exception.TicketBookingException;
import com.security.demoJWT.user.Roles;
import com.security.demoJWT.user.User;
import com.security.demoJWT.user.UserRepository;
import com.security.demoJWT.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Autowired
    RoleCustomRepo roleCustomRepo;
    @Autowired
    UserService userService;
//    public AuthenticationResponse register(RegisterRequest request) {
//       var user = User.builder()
//               .firstName(request.getFirstname())
//               .lastName(request.getLastname())
//               .gender(request.getGender())
//               .DOB(request.getDOB())
//               .email(request.getEmail())
//               .password(passwordEncoder.encode(request.getPassword()))
//               .role(Role.USER)
//               .build();
//
//       userRepository.save(user);
//       var jwtToken=jwtService.generateToken(user);
//       return AuthenticationResponse.builder()
//               .access_token(jwtToken)
//               .build();
//    }

    public ResponseEntity<?> register(RegisterRequest request) {
        try {
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if (user.isPresent()) {
                throw new TicketBookingException("User is already present");
            }
            userService.saveUser(new User(request.getFirstname(), request.getLastname(), request.getDOB(), request.getGender(), request.getEmail(), new HashSet<>(), request.getPassword()));
            userService.addToUser(request.getEmail(), "ROLE_USER");
            User user1 = userRepository.findByEmail(request.getEmail()).orElseThrow();
            return ResponseEntity.ok(user1);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        try {


            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            List<Roles> role = null;
            if (user != null) {
                role = roleCustomRepo.getRole(user);
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Roles> set = new HashSet<>();
            role.stream().forEach(c -> {
                set.add(new Roles(c.getName()));
                authorities.add(new SimpleGrantedAuthority(c.getName()));
            });
            user.setRoles(set);
            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
            var jwtAccessToken = jwtService.genTok(user, authorities);
            var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
            return ResponseEntity.ok(AuthenticationResponse.builder().access_token(jwtAccessToken).refresh_token(jwtRefreshToken).email(user.getEmail()).user_name(user.getFirstName()).build());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .a(jwtToken)
//                .build();
    }
}
