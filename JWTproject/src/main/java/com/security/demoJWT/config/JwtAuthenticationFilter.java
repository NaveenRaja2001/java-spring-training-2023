package com.security.demoJWT.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.demoJWT.entity.TokenAndStatus;
import com.security.demoJWT.exception.TicketBookingException;
import com.security.demoJWT.repo.TokenAndStatusRepository;
import com.security.demoJWT.repo.TokenExpiredRepository;
import com.security.demoJWT.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
@Autowired
private final UserRepository userRepository;

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private TokenAndStatusRepository tokenAndStatusRepository;

    @Autowired
    TokenExpiredRepository tokenExpiredRepository;
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
         String authHeader = request.getHeader(AUTHORIZATION);
         if(authHeader!=null && authHeader.startsWith("Bearer ")){
             try{
                 String token=authHeader.substring("Bearer ".length());
                 TokenAndStatus tokenAndStatus=null;
                 Timestamp currentTimestamp=new Timestamp(System.currentTimeMillis());



                 if(tokenAndStatusRepository.existsByToken(token)){
                     tokenAndStatus=tokenAndStatusRepository.findByToken(token);
                     tokenAndStatus.setTimestamp(currentTimestamp);
                     System.out.print(tokenAndStatus.getTimestamp());
                 }
                 else {
                     tokenAndStatus=new TokenAndStatus();
                     tokenAndStatus.setToken(token);
                     tokenAndStatus.setTimestamp(currentTimestamp);
                 }

                 tokenAndStatusRepository.save(tokenAndStatus);


                 Algorithm algorithm=Algorithm.HMAC256((secretKey.getBytes()));
                 JWTVerifier verifier=JWT.require(algorithm).build();
                 DecodedJWT decodedJWT=verifier.verify(token);
                 String username=decodedJWT.getSubject();
                 if(tokenExpiredRepository.existsByToken(token)){
                     throw new TicketBookingException("Blocked Token");
                 }
                 userRepository.findByEmail(username).orElseThrow(()->new Exception("Invalid Token"));
                 String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                 Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
                 Arrays.stream(roles).forEach(role->{
                     authorities.add(new SimpleGrantedAuthority(role));
                 });
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,authorities);
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                 filterChain.doFilter(request,response);
             } catch (Exception e) {
                 throw new TicketBookingException(e);
             }
         }else {
             filterChain.doFilter(request,response);
         }
    }

}
