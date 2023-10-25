package com.training.helper.cron;

import com.training.helper.constants.CommonConstants;
import com.training.helper.entities.User;
import com.training.helper.exception.HelperAppException;
import com.training.helper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This cron class run every 12am to delete the rejected user
 *
 * @Author Naveen Raja
 */
@Component
public class RejectedUser {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void fixedRateSch() {
        try {
            List<User> rejectedUser = userRepository.findAllByStatus(CommonConstants.STATUS_REJECTED);
            userRepository.deleteAll(rejectedUser);
        }catch (Exception e){
            throw new HelperAppException(e.getMessage());
        }
    }
}

