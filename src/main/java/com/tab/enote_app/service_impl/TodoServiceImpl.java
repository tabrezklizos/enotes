package com.tab.enote_app.service_impl;


import com.tab.enote_app.dto.TodoDto;
import com.tab.enote_app.entity.Todo;
import com.tab.enote_app.enums.TodoStatus;
import com.tab.enote_app.exception.ResourceNotFoundException;
import com.tab.enote_app.repository.TodoRepository;
import com.tab.enote_app.service.TodoService;
import com.tab.enote_app.util.CommonUtil;
import com.tab.enote_app.util.Validation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final  TodoRepository todoRepository;
    private final  ModelMapper mapper;
    private final  Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {

        validation.todoValidation(todoDto);

        Todo todo = mapper.map(todoDto, Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo save = todoRepository.save(todo);
        if(!ObjectUtils.isEmpty(save)){
            return true;
        }
        return false;
    }

    @Override
    public TodoDto findById(Integer id) throws Exception {
        Todo todo = todoRepository.
                    findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("todo not found or id Invalid"));
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto,Todo todo){

        for(TodoStatus ts :TodoStatus.values()){
            if(ts.getId().equals(todo.getStatusId())){
                todoDto.getStatus().setName(ts.getName());
            }
        }

    }

    @Override
    public List<TodoDto> findByUserId() {
        Integer userId= CommonUtil.getLogUser().getId();
        List<Todo> todoList = todoRepository.findByCreatedBy(userId);
        List<TodoDto> todoDtoList = todoList.stream().map((element) -> mapper.map(element, TodoDto.class)).toList();
        return todoDtoList;
    }
}




































