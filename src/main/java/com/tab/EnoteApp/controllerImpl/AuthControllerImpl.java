package com.tab.EnoteApp.controllerImpl;

import com.tab.EnoteApp.controller.AuthController;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Autowired
    AuthService userService;

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
