package com.tab.EnoteApp.util;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto){

        Map<String,Object> error = new LinkedHashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("category or json data is not present");
        }
        else{

            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","name is empty or null");
            }else{

                if(categoryDto.getName().length()<3){
                    error.put("name","name atleast be 3 char");
                }
                if(categoryDto.getName().length()>10){
                    error.put("name","name atmost be 10 char");
                }
            }

            if(ObjectUtils.isEmpty(categoryDto.getIsActive())){
                error.put("isActive","description is empty or null");
            }
            else{

                if(categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
                        && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()){
                    error.put("isActive","invalid isActive field");
                }
            }
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description","description is empty or null");
            }

        }

        if(!error.isEmpty()){
            throw new ValidationException(error);
        }


    }


}
