package com.tab.enote_app.service;


import com.tab.enote_app.dto.TodoDto;

import java.util.List;

public interface TodoService {

    Boolean saveTodo(TodoDto todoDto) throws Exception;
    TodoDto findById(Integer id) throws Exception;
     List<TodoDto> findByUserId();

}
