package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.LoginRequest;
import com.tab.EnoteApp.dto.LoginResponse;
import com.tab.EnoteApp.dto.UserDto;

public interface UserService {
    public Boolean register(UserDto userDto, String url) throws Exception;
    LoginResponse login(LoginRequest request);
}
