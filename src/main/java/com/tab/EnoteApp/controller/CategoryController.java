package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class    CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCatgory = categoryService.saveCategory(categoryDto);
        if(saveCatgory){
            return new ResponseEntity<>("saved success", HttpStatus.CREATED );
        }
        return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/active-categories")
    public ResponseEntity<?> getCategories(){
        List<CategoryResponse> allCatgeory= categoryService.getActiveCatgeory();

        if(CollectionUtils.isEmpty(allCatgeory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCatgeory,HttpStatus.OK);
    }
}
