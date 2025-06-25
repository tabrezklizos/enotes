package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.service.HomeService;
import com.tab.EnoteApp.service.UserService;
import com.tab.EnoteApp.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?>verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        Boolean savedUser = homeService.verifyAccount(uid, code);
        if(savedUser) {
            return CommonUtil.createResponseMessage("verified", HttpStatus.OK);
        }
        return CommonUtil.createResponseMessage("error in verification code",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception {
            userService.sendEmailForPasswordReset(email,request);
        return CommonUtil.createResponseMessage("Email send success !! Check Email Reset Password",HttpStatus.OK);
    }
    @PostMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPwsdLink(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        userService.verifyPswdResetLink(uid,code);
        return CommonUtil.createResponseMessage("verification success",HttpStatus.OK);
    }

}
