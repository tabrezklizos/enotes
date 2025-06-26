package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.exception.ResourceExistsException;
import com.tab.EnoteApp.exception.ResourceNotFoundException;
import com.tab.EnoteApp.repository.CategoryRepository;
import com.tab.EnoteApp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
     private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) throws Exception {

        Optional <Category> categoryExisted= categoryRepository
                .findByNameAndIsDeletedFalse(categoryDto.getName());


        if(categoryExisted.isPresent()){

            log.info(" existedCaregory: {}",categoryExisted.get());

            throw new ResourceExistsException("resource exist exception");
        }


        Category category = mapper.map(categoryDto, Category.class);

        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
          //  category.setCreatedBy(1);
          //  category.setCreatedOn(new Date());
        }
        else{
            updateCategory(category);
        }

       Category saveCategory = categoryRepository.save(category);

        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) {

        Optional<Category> byId = categoryRepository.findById(category.getId());

        if(byId.isPresent()){

            Category existingCategory = byId.get();

            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setIsDeleted(existingCategory.getIsDeleted());

           // category.setUpdatedOn(new Date());
           // category.setUpdatedBy(1);

        }

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
    public CategoryDto getCategoryByIdAndIsDeletedFalse(Integer id) throws Exception {
        Category category= categoryRepository
                                    .findByIdAndIsDeletedFalse(id)
                                    .orElseThrow(()->new ResourceNotFoundException("Category with id "+id+" is not found"));

        if(!ObjectUtils.isEmpty(category)){

            if(category.getName()==null){
                throw new IllegalArgumentException("name is null");
            }

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
