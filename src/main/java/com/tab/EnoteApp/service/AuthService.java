package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.LoginRequest;
import com.tab.EnoteApp.dto.LoginResponse;
import com.tab.EnoteApp.dto.UserRequest;

public interface AuthService {
    public Boolean register(UserRequest userRequest, String url) throws Exception;
    LoginResponse login(LoginRequest request);
}
