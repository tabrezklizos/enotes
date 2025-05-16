package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.repository.CategoryRepository;
import com.tab.EnoteApp.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
     private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        Category category = mapper.map(categoryDto, Category.class);

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
    public List<CategoryResponse> getActiveCatgeory() {

        List<Category> categoryList = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> categoryResponselist
                = categoryList.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryResponselist;
    }
}
