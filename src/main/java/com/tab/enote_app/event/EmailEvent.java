package com.tab.enote_app.event;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailEvent implements Serializable {
    private String email;
    private String firstName;
    private Integer userId;
    private String verificationCode;
    private String baseUrl;
}
