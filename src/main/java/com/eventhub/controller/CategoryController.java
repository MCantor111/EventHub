package com.eventhub.controller;

import com.eventhub.dto.CategoryDTO;
import com.eventhub.dto.CreateCategoryDTO;
import com.eventhub.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "List all categories")
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Create a category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDTO createCategory(@Valid @RequestBody CreateCategoryDTO dto) {
        return categoryService.createCategory(dto);
    }
}