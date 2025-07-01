package com.tab.enote_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PswdResetRequest {
    private Integer uid;
    private String newPassword;
}
