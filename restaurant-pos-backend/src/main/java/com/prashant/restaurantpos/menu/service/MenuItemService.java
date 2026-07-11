package com.prashant.restaurantpos.menu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prashant.restaurantpos.menu.dto.MenuItemRequest;
import com.prashant.restaurantpos.menu.dto.MenuItemResponse;

public interface MenuItemService {

    MenuItemResponse create(MenuItemRequest request);

    Page<MenuItemResponse> getPage(
            String search,
            Pageable pageable);

    MenuItemResponse getById(Long id);

    MenuItemResponse update(Long id, MenuItemRequest request);

    void delete(Long id);

}