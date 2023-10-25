package com.training.helper.projection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(List<String> notifyUser, Integer userId) {

        try {
            for(String notifyUserEmail:notifyUser) {
                SimpleMailMessage mailMessage
                        = new SimpleMailMessage();

                // Setting up necessary details
                mailMessage.setFrom(sender);
                mailMessage.setTo(notifyUserEmail);
                mailMessage.setText(userId + "has been deleted");
                mailMessage.setSubject("Cancelling of your booked appointment");

                javaMailSender.send(mailMessage);
            }
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
}