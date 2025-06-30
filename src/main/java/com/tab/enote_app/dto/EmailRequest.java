package com.tab.enote_app.dto;

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
