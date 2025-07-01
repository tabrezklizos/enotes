package com.tab.enote_app.entity;

import lombok.Data;
// ✅ Correct (Jakarta EE 10+)
import jakarta.persistence.*;


@Data
@Entity
public class Category extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isDeleted;


}
