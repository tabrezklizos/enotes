package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.TodoDto;
import com.tab.EnoteApp.service.TodoService;
import com.tab.EnoteApp.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception {
        Boolean todoSaved = todoService.saveTodo(todoDto);
        if(todoSaved){
            return CommonUtil.createResponseMessage("todo saved success", HttpStatus.CREATED);

        }
        return CommonUtil.errorResponseMessage("todo not saved success",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception {
        TodoDto todoDto = todoService.findById(id);
        return CommonUtil.createResponse(todoDto, HttpStatus.FOUND);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTodoByUserId() throws Exception {
        List<TodoDto> toDoDtoList = todoService.findByUserId();

        if(CollectionUtils.isEmpty(toDoDtoList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createResponse(toDoDtoList, HttpStatus.FOUND);
    }


}
