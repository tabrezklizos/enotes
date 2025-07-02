package com.tab.enote_app.service_impl;

import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.dto.CategoryResponse;
import com.tab.enote_app.entity.Category;
import com.tab.enote_app.exception.ResourceExistsException;
import com.tab.enote_app.repository.CategoryRepository;
import com.tab.enote_app.util.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Validation validation;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Mock
    private ModelMapper mapper;

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

        //category = mapper.map(categoryDto, Category.class);
    }

    @Test
    public void testSaveCategory() throws Exception {
        //arrange
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
        when(mapper.map(categoryDto,Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        //act
        Boolean saveCategory = categoryServiceImpl.saveCategory(categoryDto);

        //assert
        assertTrue(saveCategory);

        //verify
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository).save(category);

    }

    @Test
    public void testCategoryExist(){

        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);

        ResourceExistsException exception = assertThrows(ResourceExistsException.class, () ->
                categoryServiceImpl.saveCategory(categoryDto));

        assertEquals("Category already exist", exception.getMessage());
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository,never()).save(category);
    }

    @Test
    public void testUpdateCategory() throws Exception {

        categoryDto.setId(1);
        category.setId(1);

        //arrange
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
        when(mapper.map(categoryDto,Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        //act
        Boolean saveCategory = categoryServiceImpl.saveCategory(categoryDto);

        //assert
        assertTrue(saveCategory);

        //verify
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository).save(category);
    }

    @Test
    public void testGetAllCategory(){

        //arrange
        when(categoryRepository.findByIsDeletedFalse()).thenReturn(categories);

        //act
        List<CategoryResponse> allCategory = categoryServiceImpl.getAllCategory();

        //assert
        assertEquals(categories.size(),allCategory.size());

        //verify
        verify(categoryRepository).findByIsDeletedFalse();

    }


}



























