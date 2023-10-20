package com.training.helper.cron;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.training.helper.constants.CommonConstants;
import com.training.helper.entities.TokenExpired;
import com.training.helper.entities.User;
import com.training.helper.exception.HelperAppException;
import com.training.helper.repository.TokenExpiredRepository;
import com.training.helper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This cron class run every 3hrs to delete the expired tokens
 *
 * @Author Naveen Raja
 */
@Component
public class ScheduleMessage {
    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private TokenExpiredRepository tokenExpiredRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = CommonConstants.TIME_LIMIT)
    public void fixedRateSch() {
        List<TokenExpired> tokenList = tokenExpiredRepository.findAll();
        List<TokenExpired> tokenExpiredList = tokenList.stream()
                .filter(value -> {
                    String token = value.getToken();
                    DecodedJWT decodedJWT = JWT.decode(token);
                    long remainingTime = decodedJWT.getExpiresAt().getTime() - new Date().getTime();
                    return remainingTime <= 0;
                })
                .collect(Collectors.toList());
        if(!tokenExpiredList.isEmpty()) {
            tokenExpiredRepository.deleteAll(tokenExpiredList);
        }
    }
}
