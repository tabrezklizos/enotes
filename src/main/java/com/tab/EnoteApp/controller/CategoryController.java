package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.tab.EnoteApp.util.Constants.ROLE_ADMIN;
import static com.tab.EnoteApp.util.Constants.ROLE_ADMIN_USER;

@Tag(name="Category")
@RequestMapping("api/v1/category")
public interface CategoryController {

    @Operation(summary = "Save Category",tags = {"Category"},description ="Admin Save Category")
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) throws Exception;

    @Operation(summary = "Get All Category",tags = {"Category"},description ="Admin Get All Category")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get All Active Category",tags = {"Category"},description ="Admin Or User Can Get All Active Category")
    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getAllActiveCategory();

    @Operation(summary = "Get Category By Id",tags = {"Category"},description ="Admin Can Get Category")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?>getCategoryByIdAndIsDeletedFalse(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Delete Category",tags = {"Category"},description ="Admin Delete Category")
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?>deleteCategoryById(@PathVariable Integer id);
}
