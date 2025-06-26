package com.tab.EnoteApp.repository;

import com.tab.EnoteApp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {


    List<Todo> findByCreatedBy(Integer createdBy);
}





