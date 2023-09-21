package com.security.demoJWT.cron;


import com.security.demoJWT.entity.TokenAndStatus;
import com.security.demoJWT.entity.TokenExpired;
import com.security.demoJWT.repo.TokenAndStatusRepository;
import com.security.demoJWT.repo.TokenExpiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

/**
 * This cron class run every 5sec once to find the user inactive status
 * @Author Naveen
 */
@Component
public class ScheduleMessage {
    @Autowired
    TokenAndStatusRepository tokenAndStatusRepository;

    @Autowired
    TokenExpiredRepository tokenExpiredRepository;

    @Scheduled(fixedRate = 5000)
    public void fixedRateSch() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        List<TokenAndStatus> tokenAndStatuses = tokenAndStatusRepository.findAll();
        if (!tokenAndStatuses.isEmpty()) {
            System.out.println("currentTimeStamp:::::::::::::::::::::::::::::::::::::::::::::::::" + currentTimestamp);
            for (TokenAndStatus i : tokenAndStatuses) {
                Duration duration = Duration.between(i.getTimestamp().toLocalDateTime(), currentTimestamp.toLocalDateTime());
                long minutesDifference = duration.toMinutes();
                System.out.println("Difference in minutes : " + minutesDifference);
                if (minutesDifference >= 3) {
                    TokenExpired tokenExpired = new TokenExpired();
                    tokenExpired.setToken(i.getToken());
                    tokenExpiredRepository.save(tokenExpired);
                    tokenAndStatusRepository.delete(i);
                }
            }
        }
    }
}
