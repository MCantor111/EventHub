package com.eventhub.service;

import com.eventhub.dto.CategoryDTO;
import com.eventhub.dto.CreateCategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO createCategory(CreateCategoryDTO dto);
}