package com.eventhub.service.impl;

import com.eventhub.dto.CategoryDTO;
import com.eventhub.dto.CreateCategoryDTO;
import com.eventhub.model.Category;
import com.eventhub.repository.CategoryRepository;
import com.eventhub.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .toList();
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Category category = new Category(dto.getName());
        Category saved = categoryRepository.save(category);
        return new CategoryDTO(saved.getId(), saved.getName());
    }
}