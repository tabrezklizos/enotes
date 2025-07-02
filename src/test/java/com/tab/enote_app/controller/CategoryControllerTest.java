package com.tab.enote_app.controller;

import com.tab.enote_app.controller_impl.CategoryControllerImpl;
import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.entity.Category;
import com.tab.enote_app.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryControllerImpl categoryControllerImpl;

    private CategoryDto categoryDto = null;
    private Category category = null;
    private List<Category> categories=new ArrayList<>();
    private List<CategoryDto> categoriesDto=new ArrayList<>();

    @BeforeEach
    public void initialize() {

        categoryDto  = CategoryDto.builder()
                .id(null)
                .name("Java Notes")
                .description("It is an Effective Java, written By Blouch")
                .isActive(true)
                .build();


        category=Category.builder()
                .id(null)
                .name("Java Notes")
                .description("It is an Effective Java, written By Blouch")
                .isActive(true)
                .isDeleted(false)
                .build();

        categories.add(category);
        categoriesDto.add(categoryDto);

    }

    @Test
    public void testSaveCategory() throws Exception {

        //arrange
        when(categoryService.saveCategory(categoryDto)).thenReturn(true);

        //act
        ResponseEntity<?> response = categoryControllerImpl.saveCategory(categoryDto);
        Object body = response.getBody();
        Map<String, String> json = (Map<String, String>) body;

        //assert
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(json.get("status"),"success");


    }




}
