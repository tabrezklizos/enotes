package com.tab.EnoteApp.controllerImpl;

import com.tab.EnoteApp.controller.TodoController;
import com.tab.EnoteApp.dto.TodoDto;
import com.tab.EnoteApp.service.TodoService;
import com.tab.EnoteApp.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoControllerImpl implements TodoController {

    @Autowired
    private TodoService todoService;

    @Override
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception {
        Boolean todoSaved = todoService.saveTodo(todoDto);
        if(todoSaved){
            return CommonUtil.createResponseMessage("todo saved success", HttpStatus.CREATED);

        }
        return CommonUtil.errorResponseMessage("todo not saved success",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception {
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
