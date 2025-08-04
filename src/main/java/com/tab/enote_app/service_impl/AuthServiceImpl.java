package com.tab.enote_app.service_impl;

import com.tab.enote_app.config.security.CustomUserDetails;
import com.tab.enote_app.dto.LoginRequest;
import com.tab.enote_app.dto.LoginResponse;
import com.tab.enote_app.dto.UserRequest;
import com.tab.enote_app.dto.UserResponse;
import com.tab.enote_app.entity.AccountStatus;
import com.tab.enote_app.entity.Role;
import com.tab.enote_app.entity.Token;
import com.tab.enote_app.entity.User;
import com.tab.enote_app.event.EmailEvent;
import com.tab.enote_app.repository.RoleRepository;
import com.tab.enote_app.repository.TokenRepository;
import com.tab.enote_app.repository.UserRepository;
import com.tab.enote_app.service.AuthService;
import com.tab.enote_app.service.JwtService;
import com.tab.enote_app.util.EmailService;
import com.tab.enote_app.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validation validation;
    private final ModelMapper mapper;
    private final  EmailService emailService;
    private final  AuthenticationManager authenticationManager;
    private final  JwtService jwtService;
    private final  BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher eventPublisher;

/*    @Override
    public Boolean register(UserRequest userRequest, String url) throws Exception {
        validation.userValidation(userRequest);

        log.info("out of the validation ");

        User user = mapper.map(userRequest, User.class);

        setRole(userRequest,user);

        AccountStatus status =AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();

          user.setStatus(status);
          user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        if (savedUser.getId() == null) return false;

        //send Email
          eventPublisher.publishEvent(new UserRegisteredEvent(this, savedUser, url));
        //sendEmailForRegister(savedUser,url);
        return true;
    }*/

    private final AmqpTemplate amqpTemplate;
    private final TokenRepository tokenRepository;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Override
    public Boolean register(UserRequest userRequest, String url) throws Exception {
        validation.userValidation(userRequest);

        User user = mapper.map(userRequest, User.class);
        setRole(userRequest, user);

        AccountStatus status = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();

        user.setStatus(status);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        if (savedUser.getId() == null) return false;

        // Publish EmailEvent to RabbitMQ
        EmailEvent emailEvent = EmailEvent.builder()
                    .email(savedUser.getEmail())
                    .firstName(savedUser.getFirstName())
                    .userId(savedUser.getId())
                    .verificationCode(savedUser.getStatus().getVerificationCode())
                    .baseUrl(url)
                    .build();

        amqpTemplate.convertAndSend(exchange, routingKey, emailEvent);

        return true;
    }

  /*  @Async
    @EventListener
    public void sendEmailForRegister(UserRegisteredEvent event) throws Exception {

        User savedUser = event.getUser();
        String url = event.getUrl();

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


    }*/

    private void setRole(UserRequest userRequest, User user) {
        List<Integer> roleIds = userRequest.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(roles);
    }


    @Override
    public LoginResponse login(LoginRequest request) {

         Authentication authenticate = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if(authenticate.isAuthenticated()){
                CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
                User user=customUserDetails.getUser();
                String jwt = jwtService.generateToken(user);

                //set logout true from old token
                revokeAllTokenByUser(user);

                // save new token
                saveUserToken(jwt, user);

                LoginResponse response = LoginResponse.builder()
                        .user(mapper.map(user, UserResponse.class))
                        .token(jwt)
                        .build();
                return response;
            }
        return null;
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> allValidTokenByUser = tokenRepository.findAllTokenByUser(user.getId());

        if(!allValidTokenByUser.isEmpty()){
            allValidTokenByUser.forEach(t -> {
                t.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(allValidTokenByUser);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setLoggedOut(false);
        tokenRepository.save(token);
    }
}
