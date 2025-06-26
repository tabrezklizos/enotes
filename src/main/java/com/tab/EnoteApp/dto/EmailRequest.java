package com.tab.EnoteApp.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String to;
    private String title;
    private String subject;
    private String message;


}
