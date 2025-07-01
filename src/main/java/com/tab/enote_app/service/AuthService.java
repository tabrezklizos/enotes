package com.tab.enote_app.service;

import com.tab.enote_app.dto.LoginRequest;
import com.tab.enote_app.dto.LoginResponse;
import com.tab.enote_app.dto.UserRequest;

public interface AuthService {
    public Boolean register(UserRequest userRequest, String url) throws Exception;
    LoginResponse login(LoginRequest request);
}
