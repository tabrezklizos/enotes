package com.tab.EnoteApp.repository;

import com.tab.EnoteApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
