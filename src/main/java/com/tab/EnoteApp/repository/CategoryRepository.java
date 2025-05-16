package com.tab.EnoteApp.repository;

import com.tab.EnoteApp.dto.CategoryDto;
import com.tab.EnoteApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByIsActiveTrue();
}
