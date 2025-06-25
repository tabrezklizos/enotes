package com.tab.EnoteApp.serviceImpl;


import com.tab.EnoteApp.dto.TodoDto;
import com.tab.EnoteApp.entity.Todo;
import com.tab.EnoteApp.enums.TodoStatus;
import com.tab.EnoteApp.exception.ResourceNotFoundException;
import com.tab.EnoteApp.repository.TodoRepository;
import com.tab.EnoteApp.service.TodoService;
import com.tab.EnoteApp.util.CommonUtil;
import com.tab.EnoteApp.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Validation validation;

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




































