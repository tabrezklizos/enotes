package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.repository.CategoryRepository;
import com.tab.EnoteApp.service.CategoryService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<CategoryResponse> getActiveCategoryAndIsDeletedFalse() {

        List<Category> categoryList = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponselist
                = categoryList.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryResponselist;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {

        List<Category> categoryList = categoryRepository.findByIsDeletedFalse();
        List<CategoryResponse> categoryResponselist
                = categoryList.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryResponselist;
    }


    @Override
    public CategoryDto getCategoryByIdAndIsDeletedFalse(Integer id) {
        Optional<Category> optionalCategory= categoryRepository.findByIdAndIsDeletedFalse(id);

        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            return mapper.map(category,CategoryDto.class);
        }

        return null;
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {

        Optional<Category> optionalCategory= categoryRepository.findById(id);

        if(optionalCategory.isPresent()){

            Category category = optionalCategory.get();
                     category.setIsDeleted(true);
                     categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
