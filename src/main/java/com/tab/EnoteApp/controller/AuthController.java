package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.LoginRequest;
import com.tab.EnoteApp.dto.LoginResponse;
import com.tab.EnoteApp.dto.UserRequest;
import com.tab.EnoteApp.service.AuthService;
import com.tab.EnoteApp.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService userService;

    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception {

        String url=CommonUtil.getUrl(request);

        Boolean register = userService.register(userRequest,url);
        if(register){
            return CommonUtil.errorResponseMessage("saved user", HttpStatus.CREATED);
        }
        return CommonUtil.errorResponseMessage("user not saved", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        LoginResponse loginResponse = userService.login(request);

        if(ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.errorResponseMessage("invalid Credential", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createResponse(loginResponse, HttpStatus.OK);

    }



}
