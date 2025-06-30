package com.tab.enote_app.controller;

import com.tab.enote_app.dto.LoginRequest;
import com.tab.enote_app.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name="Authentication",description = "all the user authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @ApiResponses(value = {@ApiResponse(responseCode = "201",description = "Register Success"),
                            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                              @ApiResponse(responseCode = "400",description = "Bad Request")})
    @Operation(summary = "User Register Endpoints",tags = {"Authentication", "home"})
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception;

    @Operation(summary = "User Login Endpoints",tags = {"Authentication", "home"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request);
}
