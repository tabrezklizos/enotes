package com.tab.EnoteApp.service;


import com.tab.EnoteApp.dto.TodoDto;

import java.util.List;

public interface TodoService {

    Boolean saveTodo(TodoDto todoDto) throws Exception;
    TodoDto findById(Integer id) throws Exception;
     List<TodoDto> findByUserId();

}
