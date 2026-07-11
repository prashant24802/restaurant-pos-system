package com.prashant.restaurantpos.menu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.restaurantpos.menu.dto.MenuItemRequest;
import com.prashant.restaurantpos.menu.dto.MenuItemResponse;
import com.prashant.restaurantpos.menu.entity.MenuCategory;
import com.prashant.restaurantpos.menu.entity.MenuItem;
import com.prashant.restaurantpos.menu.repository.MenuCategoryRepository;
import com.prashant.restaurantpos.menu.repository.MenuItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryRepository categoryRepository;

    @Override
    public MenuItemResponse create(MenuItemRequest request) {

        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        MenuItem item = MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .category(category)
                .build();

        item = menuItemRepository.save(item);

        return mapToResponse(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getPage(
            String search,
            Pageable pageable) {

        return menuItemRepository
                .search(search, pageable)
                .map(this::mapToResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public MenuItemResponse getById(Long id) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));

        return mapToResponse(item);

    }

    @Override
    public MenuItemResponse update(
            Long id,
            MenuItemRequest request) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));

        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImageUrl(request.getImageUrl());
        item.setCategory(category);

        item = menuItemRepository.save(item);

        return mapToResponse(item);

    }

    @Override
    public void delete(Long id) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));

        menuItemRepository.delete(item);

    }

    private MenuItemResponse mapToResponse(MenuItem item) {

        return MenuItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .available(item.isAvailable())
                .imageUrl(item.getImageUrl())
                .categoryId(item.getCategory().getId())
                .categoryName(item.getCategory().getName())
                .build();

    }

}