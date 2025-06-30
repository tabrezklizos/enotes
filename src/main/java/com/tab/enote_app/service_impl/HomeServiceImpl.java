package com.tab.enote_app.service_impl;

import com.tab.enote_app.entity.AccountStatus;
import com.tab.enote_app.entity.User;
import com.tab.enote_app.exception.ResourceNotFoundException;
import com.tab.enote_app.exception.SuccessException;
import com.tab.enote_app.repository.UserRepository;
import com.tab.enote_app.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final  UserRepository userRepository;

    @Override
    public Boolean verifyAccount(Integer uid, String verificationCode) throws Exception {
        User user = userRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("invalid user"));

        if(user.getStatus().getVerificationCode()==null){
            throw new SuccessException("already verified");
        }

        if(user.getStatus().getVerificationCode().equals(verificationCode)) {

            AccountStatus status =user.getStatus();
            status.setVerificationCode(null);
            status.setIsActive(true);

            user.setStatus(status);
            userRepository.save(user);

            return true;
        }
        return false;
    }
}
