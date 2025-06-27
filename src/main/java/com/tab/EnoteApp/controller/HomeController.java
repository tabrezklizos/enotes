package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.PswdResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="home")
@RequestMapping("/api/v1/home")
public interface HomeController {

    @Operation(summary = "Verification user account",tags={"home"},description = "user verification account after register account")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @Operation(summary = "Send email for password reset",tags={"home"},description = " User can send email for password reset")
    @PostMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @Operation(summary = "verification password link",tags={"home"},description = " User verification password link")
    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPwsdLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @Operation(summary = "reset password",tags={"home"},description = " User can reset password")
    @PostMapping("/reset-pswd")
    public ResponseEntity<?> resetPswd(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
