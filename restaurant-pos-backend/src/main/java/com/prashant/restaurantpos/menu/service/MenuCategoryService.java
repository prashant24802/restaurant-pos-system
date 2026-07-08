package com.prashant.restaurantpos.menu.service;

import java.util.List;

import com.prashant.restaurantpos.menu.dto.CategoryRequest;
import com.prashant.restaurantpos.menu.dto.CategoryResponse;

public interface MenuCategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);
}