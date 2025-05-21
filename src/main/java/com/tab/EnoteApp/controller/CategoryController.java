package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.exception.ResourceExistsException;
import com.tab.EnoteApp.service.CategoryService;
import com.tab.EnoteApp.util.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/category")
public class    CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    Validation validation;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) throws Exception {

        validation.categoryValidation(categoryDto);

        Boolean saveCatgory = categoryService.saveCategory(categoryDto);
        if(saveCatgory){
            return new ResponseEntity<>("saved success", HttpStatus.CREATED );
        }
        return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCatgeory= categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCatgeory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCatgeory,HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveCategory(){
        List<CategoryResponse> allActiveCatgeory= categoryService.getActiveCategoryAndIsDeletedFalse();

        if(CollectionUtils.isEmpty(allActiveCatgeory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allActiveCatgeory,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?>getCategoryByIdAndIsDeletedFalse(@PathVariable Integer id) throws Exception{

        CategoryDto categoryDto=categoryService.getCategoryByIdAndIsDeletedFalse(id);

        if(ObjectUtils.isEmpty(categoryDto)){
            return new ResponseEntity<>("category with id "+id+" is not found",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categoryDto,HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCategoryById(@PathVariable Integer id){
        Boolean categoryDeleted=categoryService.deleteCategoryById(id);

        if(categoryDeleted){
            return new ResponseEntity<>(" category with id  "+id+" is deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("category with this id "+id+" is not found",HttpStatus.INTERNAL_SERVER_ERROR);

    }











}
