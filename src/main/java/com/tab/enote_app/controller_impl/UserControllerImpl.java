package com.tab.enote_app.controller_impl;

import com.tab.enote_app.controller.UserController;
import com.tab.enote_app.dto.PasswordChange;
import com.tab.enote_app.dto.UserResponse;
import com.tab.enote_app.entity.User;
import com.tab.enote_app.service.UserService;
import com.tab.enote_app.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final  ModelMapper mapper;
    private final  UserService userService;

    @Override
    public ResponseEntity<?> profile(){
        User logUser = CommonUtil.getLogUser();
        UserResponse userResponse = mapper.map(logUser, UserResponse.class);
       return CommonUtil.createResponse(userResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordChange passwordChange){
        userService.changePassword(passwordChange);
        return CommonUtil.createResponse("Password changed", HttpStatus.OK);
    }

}
