package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.PasswordChange;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    public void changePassword(PasswordChange passwordChange);

    public void sendEmailForPasswordReset(String email, HttpServletRequest request) throws Exception;

    void verifyPswdResetLink(Integer uid, String code) throws Exception;
}
