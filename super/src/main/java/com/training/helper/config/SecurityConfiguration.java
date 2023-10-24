package com.training.helper.config;

import com.training.helper.constants.CommonConstants;
import com.training.helper.constants.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     *  Security filter chain class
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable());
        http
                .sessionManagement(S -> S.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests(e -> e.requestMatchers("/resident/**").hasAnyAuthority(Roles.RESIDENT.getValue())
                        .requestMatchers("/user/logout").hasAnyAuthority(Roles.ADMIN.getValue(),Roles.HELPER.getValue(),Roles.RESIDENT.getValue())
                        .requestMatchers("/helpers/timeslot").hasAnyAuthority(Roles.HELPER.getValue(),Roles.RESIDENT.getValue())
                        .requestMatchers("/helpers/timeslot/**").hasAnyAuthority(Roles.HELPER.getValue(),Roles.RESIDENT.getValue())
                        .requestMatchers("/helpers/**").hasAnyAuthority(Roles.HELPER.getValue())
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority(Roles.ADMIN.getValue())
                        .anyRequest().authenticated())
                .csrf(c -> c.disable())
                .cors(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
