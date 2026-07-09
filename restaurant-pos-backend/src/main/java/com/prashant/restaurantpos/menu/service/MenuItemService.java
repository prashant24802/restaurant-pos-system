package com.prashant.restaurantpos.menu.service;

import java.util.List;

import com.prashant.restaurantpos.menu.dto.MenuItemRequest;
import com.prashant.restaurantpos.menu.dto.MenuItemResponse;

public interface MenuItemService {

    MenuItemResponse create(MenuItemRequest request);

    List<MenuItemResponse> getAll();

    MenuItemResponse getById(Long id);

    List<MenuItemResponse> search(String keyword);

    MenuItemResponse update(Long id, MenuItemRequest request);

    void delete(Long id);
}