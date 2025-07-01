package com.tab.enote_app.controller_impl;

import com.tab.enote_app.controller.HomeController;
import com.tab.enote_app.dto.PswdResetRequest;
import com.tab.enote_app.service.HomeService;
import com.tab.enote_app.service.UserService;
import com.tab.enote_app.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeControllerImpl implements HomeController {

    private final  HomeService homeService;
    private final  UserService userService;

    @Override
    public ResponseEntity<?>verifyUserAccount(Integer uid,String code) throws Exception {
        Boolean savedUser = homeService.verifyAccount(uid, code);
        if(savedUser) {
            return CommonUtil.createResponseMessage("verified", HttpStatus.OK);
        }
        return CommonUtil.createResponseMessage("error in verification code",HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<?> sendEmailForPasswordReset(String email, HttpServletRequest request) throws Exception {
            userService.sendEmailForPasswordReset(email,request);
        return CommonUtil.createResponseMessage("Email send success !! Check Email Reset Password",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> verifyPwsdLink(Integer uid,String code) throws Exception {
        userService.verifyPswdResetLink(uid,code);
        return CommonUtil.createResponseMessage("verification success",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> resetPswd(PswdResetRequest pswdResetRequest) throws Exception {
        userService.resetPassword(pswdResetRequest.getUid(), pswdResetRequest.getNewPassword());
        return CommonUtil.createResponseMessage("password reset success",HttpStatus.OK);
    }

}
