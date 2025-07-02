package com.tab.enote_app.service_impl;

import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.dto.CategoryResponse;
import com.tab.enote_app.entity.Category;
import com.tab.enote_app.exception.ResourceExistsException;
import com.tab.enote_app.exception.ResourceNotFoundException;
import com.tab.enote_app.repository.CategoryRepository;
import com.tab.enote_app.service.CacheManagerService;
import com.tab.enote_app.service.CategoryService;
import com.tab.enote_app.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final  CategoryRepository categoryRepository;
    private final  ModelMapper mapper;
    private final CacheManagerService cacheManagerService;
    private final Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) throws Exception {

        validation.categoryValidation(categoryDto);

        Boolean exist = categoryRepository.existsByName(categoryDto.getName().trim());
        if (exist) {
            // throw error
            throw new ResourceExistsException("Category already exist");
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

    void updateCategory(Category category) {

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
    @Cacheable("allActiveCategory")
    public List<CategoryResponse> getActiveCategoryAndIsDeletedFalse() {

        List<Category> categoryList = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponselist
                = categoryList.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryResponselist;
    }

    @Override
    @Cacheable("allCategory")
    public List<CategoryResponse> getAllCategory() {
        log.info(">>> Cache MISS: fetching from DB");
        List<Category> categoryList = categoryRepository.findByIsDeletedFalse();
        List<CategoryResponse> categoryResponselist
                = categoryList.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryResponselist;
    }


    @Override
    @Cacheable(value="getCategoryById", key="#id")
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
    @CacheEvict(value="getCategoryById", key="#id")
    public Boolean deleteCategoryById(Integer id) {

        Optional<Category> optionalCategory= categoryRepository.findById(id);

        if(optionalCategory.isPresent()){

            Category category = optionalCategory.get();
                     category.setIsDeleted(true);
                     categoryRepository.save(category);

            //remove from cache
            cacheManagerService.removeCacheByName(Arrays.asList("allCategory","allActiveCategory"));

            return true;
        }
        return false;
    }
}
