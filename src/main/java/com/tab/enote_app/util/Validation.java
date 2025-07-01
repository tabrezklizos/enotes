package com.tab.enote_app.util;

import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.dto.NotesDto;
import com.tab.enote_app.dto.TodoDto;
import com.tab.enote_app.dto.UserRequest;
import com.tab.enote_app.enums.TodoStatus;
import com.tab.enote_app.exception.ResourceExistsException;
import com.tab.enote_app.exception.ResourceNotFoundException;
import com.tab.enote_app.exception.ValidationException;
import com.tab.enote_app.repository.RoleRepository;
import com.tab.enote_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Validation {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public  void notesValidation(NotesDto notesDto) {

           Map<String,Object> error = new LinkedHashMap<>();

           if(ObjectUtils.isEmpty(notesDto)){
               throw  new IllegalArgumentException("notes or json data is not present");
           }
        else{
               if(ObjectUtils.isEmpty(notesDto.getTitle())){
                   error.put("title","title is empty or null");
               }
               else{
                   if(notesDto.getTitle().length()<=3){
                       error.put("title","title atleast 3");
                   }
                   if(notesDto.getTitle().length()>=10){
                       error.put("title","title atmost 10");
                   }
               }


               if(ObjectUtils.isEmpty(notesDto.getDescription())){
                   error.put("description","description is empty or null");
               }
               else{
                   if(notesDto.getTitle().length()<=3){
                       error.put("description","description atleast 3");
                   }
                   if(notesDto.getTitle().length()>=10){
                       error.put("description","description atmost 10");
                   }
               }


           }



    }

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

    public  void todoValidation(TodoDto todo) throws Exception {

        TodoDto.StatusDto reqStatus = todo.getStatus();
        Boolean statusFound=false;
        for (TodoStatus ts :TodoStatus.values()){
            if(ts.getId().equals(reqStatus.getId()))
                statusFound=true;
        }

        if(!statusFound){
            throw new ResourceNotFoundException("invalid status");
        }


    }

    public  void userValidation(UserRequest userRequest) throws Exception {

        if(!StringUtils.hasText(userRequest.getFirstName())){
            throw new IllegalArgumentException("first name is invalid");
        }

        if(!StringUtils.hasText(userRequest.getLastName())){
            throw new IllegalArgumentException("last name is invalid");
        }

        if(!StringUtils.hasText(userRequest.getEmail())
                || !userRequest.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("email is invalid");
        }
        else{
            boolean emailExist = userRepository.existsByEmail(userRequest.getEmail());
            if(emailExist){
                throw new ResourceExistsException("email already exists");
            }
        }

        if(!StringUtils.hasText(userRequest.getMobNo())
                || !userRequest.getMobNo().matches(Constants.MOB_REGEX)){
            throw new IllegalArgumentException("mobno is invalid");
        }
        if(!StringUtils.hasText(userRequest.getPassword())
                || !userRequest.getPassword().matches(Constants.PAS_REGEX)){
            throw new IllegalArgumentException("password is invalid");
        }

        if(CollectionUtils.isEmpty(userRequest.getRoles())){
            throw new IllegalArgumentException("role is invalid");
        }
        else{
            List<Integer>  roleIds = roleRepository.findAll().stream().map(r -> r.getId()).toList();

            List<Integer>invalidRoleIds= userRequest.getRoles().stream()
                    .map(r->r.getId())
                    .filter(roleId->!roleIds.contains(roleId)).toList();

            if(!CollectionUtils.isEmpty(invalidRoleIds)){
                throw new IllegalArgumentException("invalid roles "+invalidRoleIds);
            }

        }



    }




}


















