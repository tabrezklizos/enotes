package com.tab.EnoteApp.dto;

import com.tab.EnoteApp.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;
    private User user;
}
