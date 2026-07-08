package com.prashant.restaurantpos.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.menu.dto.CategoryRequest;
import com.prashant.restaurantpos.menu.dto.CategoryResponse;
import com.prashant.restaurantpos.menu.entity.MenuCategory;
import com.prashant.restaurantpos.menu.repository.MenuCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request) {

        if (categoryRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        MenuCategory category = MenuCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        category = categoryRepository.save(category);

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CategoryResponse getById(Long id) {

        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapToResponse(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {

        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        categoryRepository.save(category);

        return mapToResponse(category);
    }

    @Override
    public void delete(Long id) {

        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }

    private CategoryResponse mapToResponse(MenuCategory category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.isActive())
                .build();
    }
}