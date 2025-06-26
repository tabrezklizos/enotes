package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.exception.ResourceExistsException;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto) throws Exception;
    
    public List<CategoryResponse> getActiveCategoryAndIsDeletedFalse();

    public CategoryDto getCategoryByIdAndIsDeletedFalse(Integer id) throws Exception;

    public Boolean deleteCategoryById(Integer id);

    List<CategoryResponse> getAllCategory();
}
