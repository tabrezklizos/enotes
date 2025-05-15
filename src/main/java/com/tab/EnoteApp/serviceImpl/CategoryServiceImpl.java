package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.repository.CategoryRepository;
import com.tab.EnoteApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
     CategoryRepository categoryRepository;

    @Override
    public Boolean saveCategory(Category category) {
                 category.setIsDeleted(false);
                 category.setCreatedBy(1);
                 category.setCreatedOn(new Date());
        Category saveCategory = categoryRepository.save(category);

        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    @Override
    public List<Category> getAllCatgeory() {

        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
}
