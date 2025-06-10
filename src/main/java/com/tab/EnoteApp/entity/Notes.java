package com.tab.EnoteApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Notes  extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private FileDetails fileDetails;

}
