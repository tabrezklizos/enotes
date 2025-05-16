package com.tab.EnoteApp.service;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;

import java.util.List;

public interface CategoryService {

   public Boolean saveCategory(CategoryDto categoryDto);
   public List<CategoryResponse> getActiveCatgeory();

}
