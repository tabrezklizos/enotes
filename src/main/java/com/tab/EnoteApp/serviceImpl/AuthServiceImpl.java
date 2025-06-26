package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.config.security.CustomUserDetails;
import com.tab.EnoteApp.dto.*;
import com.tab.EnoteApp.entity.AccountStatus;
import com.tab.EnoteApp.entity.Role;
import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.repository.RoleRepository;
import com.tab.EnoteApp.repository.UserRepository;
import com.tab.EnoteApp.service.JwtService;
import com.tab.EnoteApp.service.AuthService;
import com.tab.EnoteApp.util.EmailService;
import com.tab.EnoteApp.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Validation validation;
    @Autowired
    ModelMapper mapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean register(UserRequest userRequest, String url) throws Exception {
        validation.userValidation(userRequest);
        User user = mapper.map(userRequest, User.class);

        setRole(userRequest,user);

        AccountStatus status =AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();

          user.setStatus(status);
          user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        if(!ObjectUtils.isEmpty(savedUser)){
            //send Email
            sendEmailForRegister(savedUser,url);
            return true;
        }
        return false;
    }



    private void sendEmailForRegister(User savedUser, String url) throws Exception {

        String message ="Hi,<b>[[username]]</b> " +
                "<br> your account register succesfully <br>" +
                "<br> Click to verify account<br>" +
                "<a href='[[url]]'>verify</a><br>" +
                "Thanks,<br>Enotes.com";

                 message = message.replace("[[username]]", savedUser.getFirstName());
                 message = message.replace("[[url]]",url+"/api/v1/home/verify?uid="
                          +savedUser.getId()+"&&code="+savedUser.getStatus().getVerificationCode());

        EmailRequest emailRequest = EmailRequest.builder()
                .to(savedUser.getEmail())
                .title("Account created")
                .subject("User Registered")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);


    }

    private void setRole(UserRequest userRequest, User user) {
        List<Integer> roleIds = userRequest.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(roles);
    }


    @Override
    public LoginResponse login(LoginRequest request) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if(authenticate.isAuthenticated()){
                CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
                String token = jwtService.generateToken(customUserDetails.getUser());

                LoginResponse response = LoginResponse.builder()
                        .user(mapper.map(customUserDetails.getUser(), UserResponse.class))
                        .token(token)
                        .build();
                return response;
            }

        return null;
    }



}
