package com.security.demoJWT.auth;


import com.security.demoJWT.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String DOB;
    private String gender;
    private String email;
    private String password;
    private Role role;
}
