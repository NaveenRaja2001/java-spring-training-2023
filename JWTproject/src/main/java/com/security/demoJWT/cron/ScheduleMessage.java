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



            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::" + currentTimestamp);

            for (TokenAndStatus i : tokenAndStatuses) {
                System.out.println("eeeeeeee"+i.getTimestamp().toLocalDateTime());

                Duration duration = Duration.between(i.getTimestamp().toLocalDateTime(), currentTimestamp.toLocalDateTime());

                long minutesDifference = duration.toMinutes();
                System.out.println("eeeeeeeeeeeeeeeeeeeee"+minutesDifference);
                if (minutesDifference>=3) {
                    System.out.print("lalalalalalalalalalalaalalalallalalalalalala"+i.getTimestamp());
                    TokenExpired tokenExpired = new TokenExpired();
                    tokenExpired.setToken(i.getToken());
                    tokenExpiredRepository.save(tokenExpired);
                    tokenAndStatusRepository.delete(i);
                }
            }
        }
    }
}
