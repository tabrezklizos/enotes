package com.tab.EnoteApp.controllerImpl;

import com.tab.EnoteApp.controller.HomeController;
import com.tab.EnoteApp.dto.PswdResetRequest;
import com.tab.EnoteApp.service.HomeService;
import com.tab.EnoteApp.service.UserService;
import com.tab.EnoteApp.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeControllerImpl implements HomeController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;

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
