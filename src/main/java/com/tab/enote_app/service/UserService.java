package com.tab.enote_app.service;

import com.tab.enote_app.dto.PasswordChange;
import com.tab.enote_app.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    public void changePassword(PasswordChange passwordChange);

    public void sendEmailForPasswordReset(String email, HttpServletRequest request) throws Exception;

    void verifyPswdResetLink(Integer uid, String code) throws Exception;

    void resetPassword(Integer uid, String newPassword) throws ResourceNotFoundException, Exception;
}
