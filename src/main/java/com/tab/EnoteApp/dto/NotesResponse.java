package com.tab.EnoteApp.dto;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponse {

    private List<NotesDto> notes;
    private Integer pageSize;
    private Integer pageNo;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isFirst;
    private Boolean isLast;


}
