package com.tab.EnoteApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Boolean isDeleted;
    private LocalDateTime deletedOn;

}
