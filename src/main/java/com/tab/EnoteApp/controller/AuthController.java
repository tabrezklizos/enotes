package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.LoginRequest;
import com.tab.EnoteApp.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name="Authentication",description = "all the user authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @Operation(summary = "User Register Endpoints",tags = {"Authentication", "home"})
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception;

    @Operation(summary = "User Login Endpoints",tags = {"Authentication", "home"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request);
}
