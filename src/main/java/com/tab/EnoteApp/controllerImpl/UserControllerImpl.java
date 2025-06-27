package com.tab.EnoteApp.controllerImpl;

import com.tab.EnoteApp.controller.UserController;
import com.tab.EnoteApp.dto.PasswordChange;
import com.tab.EnoteApp.dto.UserResponse;
import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.service.UserService;
import com.tab.EnoteApp.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

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
