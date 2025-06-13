package com.tab.EnoteApp.dto;


import com.tab.EnoteApp.entity.Notes;
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
