package com.tab.enote_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class CategoryDto {

    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;


}
