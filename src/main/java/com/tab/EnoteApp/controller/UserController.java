package com.tab.EnoteApp.controller;

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
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> profile(){
        User logUser = CommonUtil.getLogUser();
        UserResponse userResponse = mapper.map(logUser, UserResponse.class);
       return CommonUtil.createResponse(userResponse, HttpStatus.FOUND);
    }
    @PostMapping("/chng-pswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChange passwordChange){
        userService.changePassword(passwordChange);
        return CommonUtil.createResponse("Password changed", HttpStatus.OK);
    }

}
