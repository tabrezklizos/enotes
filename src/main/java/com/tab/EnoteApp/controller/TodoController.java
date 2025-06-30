package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.TodoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.tab.EnoteApp.util.Constants.ROLE_USER;

@Tag(name="todo")
@RequestMapping("/api/v1/todo")
public interface TodoController {

    @Operation(summary ="create todo",tags = {"todo", "notes"},description ="user can create todo")
    @PostMapping("/save")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

    @Operation(summary ="get todo by id",tags = {"todo", "notes"},description ="user can get perticular todo by its id")
    @GetMapping("/{id}")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @Operation(summary ="get todo for user",tags = {"todo", "notes"},description ="user can get todos")
    @GetMapping("/list")
    @PreAuthorize( ROLE_USER)
    public ResponseEntity<?> getTodoByUserId() throws Exception;
}
