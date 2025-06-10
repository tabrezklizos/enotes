package com.tab.EnoteApp.dto;
import com.tab.EnoteApp.entity.FileDetails;
import lombok.Data;
import java.util.Date;

@Data
public class NotesDto {

    private Integer id;

    private String title;
    private String description;
    private CategoryDto category;
    private FileDetails fileDetails;

    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;

    @Data
    public static class FileDetails {
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
