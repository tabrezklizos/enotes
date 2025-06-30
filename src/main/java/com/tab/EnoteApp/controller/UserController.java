package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.PasswordChange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name="user")
@RequestMapping("api/v1/user")
public interface UserController {

    @Operation(summary = "get user profile",tags = {"user", "notes"},description = "get user profile")
    @GetMapping("/profile")
    public ResponseEntity<?> profile();

    @Operation(summary = "change password",tags = {"user", "notes"},description = "user can change password")
    @PostMapping("/chng-pswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChange passwordChange);
}
