package com.tab.enote_app.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavNoteDto {
    private Integer id;
    private NotesDto note;
    private Integer userId;
}
