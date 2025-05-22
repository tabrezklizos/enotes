package com.tab.EnoteApp.dto;

import lombok.Data;

import java.util.Date;

@Data
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
