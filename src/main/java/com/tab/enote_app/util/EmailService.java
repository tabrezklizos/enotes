package com.tab.enote_app.util;

import com.tab.enote_app.dto.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;

    public void sendEmail(EmailRequest emailReq) throws Exception {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, emailReq.getTitle());
        helper.setTo(emailReq.getTo());
        helper.setSubject(emailReq.getSubject());
        helper.setText(emailReq.getMessage(),true);

        emailSender.send(message);
    }


}
