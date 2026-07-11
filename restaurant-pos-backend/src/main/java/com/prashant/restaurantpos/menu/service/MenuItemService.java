package com.prashant.restaurantpos.menu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prashant.restaurantpos.menu.dto.MenuItemRequest;
import com.prashant.restaurantpos.menu.dto.MenuItemResponse;

public interface MenuItemService {

    MenuItemResponse create(MenuItemRequest request);

    List<MenuItemResponse> getAll();

    Page<MenuItemResponse> getPage(Pageable pageable);

    MenuItemResponse getById(Long id);

    List<MenuItemResponse> search(String keyword);

    MenuItemResponse update(Long id, MenuItemRequest request);

    void delete(Long id);

}