package com.tab.enote_app.controller_impl;

import com.tab.enote_app.controller.TodoController;
import com.tab.enote_app.dto.TodoDto;
import com.tab.enote_app.service.TodoService;
import com.tab.enote_app.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoControllerImpl implements TodoController {

    private final  TodoService todoService;

    @Override
    public ResponseEntity<?> saveTodo(TodoDto todoDto) throws Exception {
        Boolean todoSaved = todoService.saveTodo(todoDto);
        if(todoSaved){
            return CommonUtil.createResponseMessage("todo saved success", HttpStatus.CREATED);

        }
        return CommonUtil.errorResponseMessage("todo not saved success",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getTodoById(Integer id) throws Exception {
        TodoDto todoDto = todoService.findById(id);
        return CommonUtil.createResponse(todoDto, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> getTodoByUserId() throws Exception {
        List<TodoDto> toDoDtoList = todoService.findByUserId();

        if(CollectionUtils.isEmpty(toDoDtoList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(toDoDtoList, HttpStatus.FOUND);
    }


}
