package com.security.demoJWT.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController{

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return service.authenticate(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(  @NonNull HttpServletRequest request){
        return service.logout(request);
    }

}
