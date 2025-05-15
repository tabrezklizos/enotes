package com.tab.EnoteApp.service;

import com.tab.EnoteApp.entity.Category;

import java.util.List;

public interface CategoryService {

   public Boolean saveCategory(Category category);
   public List<Category> getAllCatgeory();

}
