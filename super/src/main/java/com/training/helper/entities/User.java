package com.training.helper.entities;

import com.training.helper.constants.CommonConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Bean class for user
 *
 * @Author Naveen Raja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = CommonConstants.REQUIRED_USERNAME)
    @Pattern(regexp = CommonConstants.USERNAME_PATTERN, message = CommonConstants.USERNAME_PATTERN_MESSAGE)
    private String firstName;
    private String lastName;

    @NotNull(message = CommonConstants.REQUIRED_DOB)
    @Pattern(regexp = CommonConstants.DOB_PATTERN, message = CommonConstants.DOB_PATTERN_MESSAGE)
    private String DOB;

    @Pattern(regexp = CommonConstants.GENDER_PATTERN, message = CommonConstants.GENDER_PATTERN_MESSAGE)
    private String gender;

    @NotNull(message = CommonConstants.REQUIRED_EMAIL)
    @Pattern(regexp = CommonConstants.EMAIL_PATTERN, message = CommonConstants.EMAIL_PATTERN_MESSAGE)
    private String email;

    @NotNull(message = CommonConstants.REQUIRED_PASSWORD)
    @Pattern(regexp = CommonConstants.PASSWORD_PATTERN, message = CommonConstants.PASSWORD_PATTERN_MESSAGE)
    private String password;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "roles_id")
    private Roles roles;


    public User(String firstName, String lastName, String DOB, String gender, String email, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(Integer id, String firstName, String lastName, String DOB, String gender, String email, String password, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.status = status;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles.getName()));
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
