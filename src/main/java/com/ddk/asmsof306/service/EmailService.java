package com.ddk.asmsof306.service;

import com.ddk.asmsof306.model.Orders;
import com.ddk.asmsof306.security.auth.RegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Qualifier("REGISTER_QUEUE")
    private  ArrayDeque<RegisterRequest> registerRequestsQueue;

    public void placeRegisterRequest(RegisterRequest registerRequest){
        registerRequestsQueue.addLast(registerRequest);
    }
    @Scheduled(fixedDelay = 5000, initialDelay = 6000)
    public void sendEmail() throws MessagingException, IOException {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        while (!registerRequestsQueue.isEmpty()){
            RegisterRequest registerRequest=registerRequestsQueue.poll();
            mimeMessage.setTo(registerRequest.getEmail());
            mimeMessage.setSubject("Register step 2");
            mimeMessage.setText("Your account has been created, please click the link below to activate your account: http://localhost:8080/api/register/activate?token="+registerRequest);

            javaMailSender.send(mimeMessage);
        }

    }
}
