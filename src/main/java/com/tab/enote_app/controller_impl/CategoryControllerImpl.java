package com.tab.enote_app.controller_impl;

import com.tab.enote_app.controller.CategoryController;
import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.dto.CategoryResponse;
import com.tab.enote_app.service.CategoryService;
import com.tab.enote_app.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<?> saveCategory(CategoryDto categoryDto) throws Exception {

        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if(saveCategory){

            return CommonUtil.createResponseMessage("saved success",HttpStatus.CREATED);
            //return new ResponseEntity<>("saved success", HttpStatus.CREATED );
        }
        return CommonUtil.errorResponse(saveCategory,HttpStatus.INTERNAL_SERVER_ERROR);
      //  return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCategory= categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allCategory,HttpStatus.OK);
        //return new ResponseEntity<>(allCatgeory,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllActiveCategory(){
        List<CategoryResponse> allActiveCategory= categoryService.getActiveCategoryAndIsDeletedFalse();

        if(CollectionUtils.isEmpty(allActiveCategory)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(allActiveCategory,HttpStatus.OK);
       // return new ResponseEntity<>(allActiveCatgeory,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?>getCategoryByIdAndIsDeletedFalse(Integer id) throws Exception{

        CategoryDto categoryDto=categoryService.getCategoryByIdAndIsDeletedFalse(id);

        if(ObjectUtils.isEmpty(categoryDto)){
            return CommonUtil.errorResponseMessage("category with id "+id+" is not found",HttpStatus.NOT_FOUND);
            //return new ResponseEntity<>("category with id "+id+" is not found",HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createResponse(categoryDto,HttpStatus.FOUND);
      //  return new ResponseEntity<>(categoryDto,HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?>deleteCategoryById(Integer id){
        Boolean categoryDeleted=categoryService.deleteCategoryById(id);
        if(categoryDeleted){
            return CommonUtil.createResponseMessage(" category with id  "+id+" is deleted",HttpStatus.OK);
           // return new ResponseEntity<>(" category with id  "+id+" is deleted",HttpStatus.OK);
        }
        return CommonUtil.errorResponseMessage("category with this id "+id+" is not found",HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>("category with this id "+id+" is not found",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
