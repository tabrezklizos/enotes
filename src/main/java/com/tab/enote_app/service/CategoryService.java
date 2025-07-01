package com.tab.enote_app.service;

import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto) throws Exception;
    
    public List<CategoryResponse> getActiveCategoryAndIsDeletedFalse();

    public CategoryDto getCategoryByIdAndIsDeletedFalse(Integer id) throws Exception;

    public Boolean deleteCategoryById(Integer id);

    List<CategoryResponse> getAllCategory();
}
