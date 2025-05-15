package com.tab.EnoteApp.entity;

import lombok.Data;

import jakarta.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseModel {

    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;


}
