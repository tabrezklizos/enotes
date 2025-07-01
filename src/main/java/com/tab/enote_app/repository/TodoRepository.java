package com.tab.enote_app.repository;

import com.tab.enote_app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {


    List<Todo> findByCreatedBy(Integer createdBy);
}





