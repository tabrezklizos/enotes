package com.tab.enote_app.event;

import com.tab.enote_app.dto.EmailRequest;
import com.tab.enote_app.util.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleEmailEvent(EmailEvent event) {
        try {
            log.info("Received EmailEvent: {}", event);

            String message = "Hi,<b>[[username]]</b> " +
                    "<br>Your account is registered successfully.<br>" +
                    "<br>Click to verify your account:<br>" +
                    "<a href='[[url]]'>Verify</a><br>" +
                    "Thanks,<br>Enotes.com";

            message = message.replace("[[username]]", event.getFirstName());
            message = message.replace("[[url]]", event.getBaseUrl() +
                    "/api/v1/home/verify?uid=" + event.getUserId() +
                    "&&code=" + event.getVerificationCode());

            EmailRequest emailRequest = EmailRequest.builder()
                    .to(event.getEmail())
                    .title("Account created")
                    .subject("User Registered")
                    .message(message)
                    .build();

            emailService.sendEmail(emailRequest);

        } catch (Exception e) {
            log.error("Error while processing EmailEvent", e);
        }
    }
}
