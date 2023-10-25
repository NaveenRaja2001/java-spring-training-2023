package com.training.helper.cron;

import com.training.helper.entities.TokenExpired;
import com.training.helper.repository.TokenExpiredRepository;
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
class ScheduleMessageTest {
    @InjectMocks
    private ScheduleMessage scheduleMessage;

    @Mock
    private TokenExpiredRepository tokenExpiredRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void fixedRateSch() {
        TokenExpired tokenExpired=new TokenExpired();
        tokenExpired.setId(1);
        tokenExpired.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYXZlZW5AZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iXSwiZXhwIjoxNjk4MjE5MjI4fQ.AUhRYLPO28jNd7mAciQChgrXHFdAZvIxFbjGKxgLGqo");
           when(tokenExpiredRepository.findAll()).thenReturn(List.of(tokenExpired));
           try{
               scheduleMessage.fixedRateSch();
           }
           catch (Exception e){
               assertEquals(e.getMessage(),"Error occurred");
           }
    }
}