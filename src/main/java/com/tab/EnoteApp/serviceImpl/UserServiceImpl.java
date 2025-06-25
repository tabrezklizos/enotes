package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.dto.EmailRequest;
import com.tab.EnoteApp.dto.PasswordChange;
import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.exception.ResourceNotFoundException;
import com.tab.EnoteApp.repository.UserRepository;
import com.tab.EnoteApp.service.UserService;
import com.tab.EnoteApp.util.CommonUtil;
import com.tab.EnoteApp.util.Constants;
import com.tab.EnoteApp.util.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

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
        User UpdatedUser = userRepository.save(user);

        String url = CommonUtil.getUrl(request);
        sendEmailRequest(UpdatedUser,url);

    }


    private void sendEmailRequest(User user, String url) throws Exception {
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


}



















