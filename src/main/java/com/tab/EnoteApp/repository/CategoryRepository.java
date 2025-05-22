package com.tab.EnoteApp.repository;

import com.tab.EnoteApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByIsActiveTrueAndIsDeletedFalse();
    Optional<Category> findByIdAndIsDeletedFalse(Integer id);
    List<Category> findByIsDeletedFalse();
    Optional<Category> findByNameAndIsDeletedFalse(String name);
}
