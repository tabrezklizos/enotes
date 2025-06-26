package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.entity.AccountStatus;
import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.exception.ResourceNotFoundException;
import com.tab.EnoteApp.exception.SuccessException;
import com.tab.EnoteApp.repository.UserRepository;
import com.tab.EnoteApp.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;

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
