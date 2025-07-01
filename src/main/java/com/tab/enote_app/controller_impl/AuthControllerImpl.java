package com.tab.enote_app.controller_impl;

import com.tab.enote_app.controller.AuthController;
import com.tab.enote_app.dto.LoginRequest;
import com.tab.enote_app.dto.LoginResponse;
import com.tab.enote_app.dto.UserRequest;
import com.tab.enote_app.service.AuthService;
import com.tab.enote_app.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService userService;

   @Override
    public ResponseEntity<?> registerUser( UserRequest userRequest, HttpServletRequest request) throws Exception {

        String url=CommonUtil.getUrl(request);

        Boolean register = userService.register(userRequest,url);
        if(!register){
            return CommonUtil.errorResponseMessage("user not saved", HttpStatus.NOT_FOUND);
        }
       return CommonUtil.createResponseMessage("saved user", HttpStatus.CREATED);

   }


    public ResponseEntity<?> login( LoginRequest request){

        LoginResponse loginResponse = userService.login(request);

        if(ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.errorResponseMessage("invalid Credential", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createResponse(loginResponse, HttpStatus.OK);

    }



}
