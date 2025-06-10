package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.dto.CategoryResponse;
import com.tab.EnoteApp.entity.Category;
import com.tab.EnoteApp.exception.ResourceExistsException;
import com.tab.EnoteApp.handler.GenericResponse;
import com.tab.EnoteApp.service.CategoryService;
import com.tab.EnoteApp.util.CommonUtil;
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

        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if(saveCategory){

            return CommonUtil.createResponseMessage("saved succes",HttpStatus.CREATED);
            //return new ResponseEntity<>("saved success", HttpStatus.CREATED );
        }
        return CommonUtil.errorResponse(saveCategory,HttpStatus.INTERNAL_SERVER_ERROR);
      //  return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCategory= categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allCategory,HttpStatus.OK);
        //return new ResponseEntity<>(allCatgeory,HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveCategory(){
        List<CategoryResponse> allActiveCategory= categoryService.getActiveCategoryAndIsDeletedFalse();

        if(CollectionUtils.isEmpty(allActiveCategory)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allActiveCategory,HttpStatus.OK);
       // return new ResponseEntity<>(allActiveCatgeory,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?>getCategoryByIdAndIsDeletedFalse(@PathVariable Integer id) throws Exception{

        CategoryDto categoryDto=categoryService.getCategoryByIdAndIsDeletedFalse(id);

        if(ObjectUtils.isEmpty(categoryDto)){
            return CommonUtil.errorResponseMessage("category with id "+id+" is not found",HttpStatus.NOT_FOUND);
            //return new ResponseEntity<>("category with id "+id+" is not found",HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createResponse(categoryDto,HttpStatus.FOUND);
      //  return new ResponseEntity<>(categoryDto,HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCategoryById(@PathVariable Integer id){
        Boolean categoryDeleted=categoryService.deleteCategoryById(id);
        if(categoryDeleted){
            return CommonUtil.createResponseMessage(" category with id  "+id+" is deleted",HttpStatus.OK);
           // return new ResponseEntity<>(" category with id  "+id+" is deleted",HttpStatus.OK);
        }
        return CommonUtil.errorResponseMessage("category with this id "+id+" is not found",HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>("category with this id "+id+" is not found",HttpStatus.INTERNAL_SERVER_ERROR);
    }











}
