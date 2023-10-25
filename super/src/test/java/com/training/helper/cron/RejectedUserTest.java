package com.training.helper.cron;

import com.training.helper.constants.CommonConstants;
import com.training.helper.entities.HelperDetails;
import com.training.helper.entities.Roles;
import com.training.helper.entities.User;
import com.training.helper.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RejectedUserTest {
    @InjectMocks
    RejectedUser rejectedUser;

    @Mock
    UserRepository userRepository;


    @Test
    void fixedRateSch() {
        User newUser = new User(1, "Naveen", "N", "19.10.2001", "male", "naveen@gmail.com", "pass", "requested");
        Roles newRoles = new Roles();
        newRoles.setName("HELPER");
        newRoles.setDescription("helper");
        newRoles.setId(2);
        newUser.setRoles(newRoles);
        HelperDetails helperDetails = new HelperDetails();
        helperDetails.setUser(newUser);
        helperDetails.setSkill("cook");
        helperDetails.setStatus("active");
        helperDetails.setPhoneNumber(90868769L);
        helperDetails.setId(2);
        when(userRepository.findAllByStatus(CommonConstants.STATUS_REJECTED)).thenReturn(List.of(newUser));
       try{
           rejectedUser.fixedRateSch();
       }
       catch (Exception e){
           assertEquals(e.getMessage(),"Error occurred with deleting");
       }
    }
}