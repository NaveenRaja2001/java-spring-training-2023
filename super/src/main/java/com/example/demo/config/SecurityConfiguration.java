package com.example.demo.config;

import com.example.demo.constants.SuccessConstants;
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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable());
        http
                .sessionManagement(S -> S.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests(e -> e.requestMatchers("/resident/**").permitAll()
                        .requestMatchers("/helpers/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority(SuccessConstants.ROLE_ADMIN)
//                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_BUSINESS_USER")
//                        .requestMatchers("/businessUser/**").hasAnyAuthority("ROLE_BUSINESS_USER")
//                        .requestMatchers("/api/**").hasAnyAuthority("ROLE_USER")
                        .anyRequest().authenticated())
                .csrf(c -> c.disable())
                .cors(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
