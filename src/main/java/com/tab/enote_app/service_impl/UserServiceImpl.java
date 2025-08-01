package com.tab.enote_app.service_impl;

import com.tab.enote_app.dto.EmailRequest;
import com.tab.enote_app.dto.PasswordChange;
import com.tab.enote_app.entity.User;
import com.tab.enote_app.event.UserResetPassword;
import com.tab.enote_app.exception.ResourceNotFoundException;
import com.tab.enote_app.repository.UserRepository;
import com.tab.enote_app.service.UserService;
import com.tab.enote_app.util.CommonUtil;
import com.tab.enote_app.util.Constants;
import com.tab.enote_app.util.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final  PasswordEncoder passwordEncoder;
    private final  UserRepository userRepository;
    private final  EmailService emailService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void changePassword(PasswordChange passwordChange) {
        User loggedInUser = CommonUtil.getLogUser();
        String password = loggedInUser.getPassword();

        String newPassword = passwordChange.getNewPassword();
        if(newPassword.matches(Constants.PAS_REGEX)){
            throw new IllegalArgumentException("new password is not secured");
        }

        String oldPassword=passwordChange.getOldPassword();
        if(!passwordEncoder.matches(oldPassword,password)){
            throw new IllegalArgumentException("Old password does not match");
        }

        String encode = passwordEncoder.encode(newPassword);
        loggedInUser.setPassword(encode);
        userRepository.save(loggedInUser);
    }

    @Override
    public void sendEmailForPasswordReset(String email, HttpServletRequest request) throws Exception {
        User user = userRepository.findByEmail(email);

        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("User not found");
        }

        //Generate pswdtoken
        String pswdResetToken = UUID.randomUUID().toString();
        user.getStatus().setPswdVerificationToken(pswdResetToken);
        User updatedUser = userRepository.save(user);

        String url = CommonUtil.getUrl(request);

        //send Email by event
        eventPublisher.publishEvent(new UserResetPassword(this,updatedUser,url));
       // sendEmailRequest(updatedUser,url);

    }
    
    @Async
    @EventListener
    public void sendEmailRequest(UserResetPassword event) throws Exception {

        User user = event.getUser();
        String url =event.getUrl();

        String message ="Hi,<b>[[username]]</b> "
                +"<br><p>you have requested to reset your password</p>"
                +"<p> Click below link to change your password</p>"
                +"<p><a href='[[url]]'>change my password</a></p>"
                +"<p> Ignore this mail if you do remember the password,"
                +" or  if you don't requested.</p><br>"
                +"Thanks,<br>Enotes.com";

        message = message.replace("[[username]]", user.getFirstName());
        message = message.replace("[[url]]",url+"/api/v1/home/verify-pswd-link?uid="
                 +user.getId()+"&&code="+user.getStatus().getPswdVerificationToken());

        EmailRequest emailRequest = EmailRequest.builder()
                    .to(user.getEmail())
                    .title("password reset")
                    .subject("password reset link")
                    .message(message)
                    .build();

        emailService.sendEmail(emailRequest);
    }

    @Override
    public void verifyPswdResetLink(Integer uid, String code) throws Exception {
         User user = userRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("invalid user"));
         verifyPswdResetToken(user.getStatus().getPswdVerificationToken(),code);


    }


    private void verifyPswdResetToken(String existingToken, String token) {
        if(StringUtils.hasText(token)){

            if(!StringUtils.hasText(existingToken)){
                throw new IllegalArgumentException("Already password reset");
            }
            if(!existingToken.equals(token)){
                throw new IllegalArgumentException("invalid url");
            }
        }else{
            throw new IllegalArgumentException("invalid token");
        }
    }
    @Override
    public void resetPassword(Integer uid, String newPassword) throws Exception {

        User user = userRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("invalid user"));
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.getStatus().setPswdVerificationToken(null);
                    userRepository.save(user);

    }
}



















