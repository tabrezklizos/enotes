package com.tab.EnoteApp.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class NotesDto {

    private Integer id;

    private String title;
    private String description;
    private CategoryDto category;
    private FilesDto fileDetails;

    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;
    private Boolean isDeleted;
    private LocalDateTime deletedOn;

    @Data
    public static class FilesDto {
        private Integer id;
        private String originalFileName;
        private String displayFileName;
    }

    @Data
    public static class CategoryDto{
        private Integer id;
        private String name;
    }


}
