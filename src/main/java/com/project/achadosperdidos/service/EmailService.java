package com.project.achadosperdidos.service;

import com.project.achadosperdidos.service.domain.Email;
import com.project.achadosperdidos.enums.StatusEmailEnum;
import com.project.achadosperdidos.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String EMAIL_FROM = "achadosperdidos2020@gmail.com";
    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;


    public Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_FROM);
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            emailSender.send(message);

            email.setStatusEmail(StatusEmailEnum.SENT);

        } catch (MailException e){
            email.setStatusEmail(StatusEmailEnum.ERROR);
        } finally {
            return emailRepository.save(email);
        }
    }
}
