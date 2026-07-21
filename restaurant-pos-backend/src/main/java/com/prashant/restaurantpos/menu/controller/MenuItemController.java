package com.prashant.restaurantpos.menu.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.menu.dto.MenuItemRequest;
import com.prashant.restaurantpos.menu.dto.MenuItemResponse;
import com.prashant.restaurantpos.menu.service.MenuItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu/items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemResponse create(
            @Valid @RequestBody MenuItemRequest request) {

        return menuItemService.create(request);

    }

    @GetMapping
    public Page<MenuItemResponse> getItems(

            @RequestParam(required = false) String search,

            @RequestParam(required = false) Long categoryId,

            @RequestParam(required = false) Boolean available,

            Pageable pageable) {

        return menuItemService.getPage(
                search,
                categoryId,
                available,
                pageable);

    }

    @GetMapping("/{id}")
    public MenuItemResponse getById(
            @PathVariable Long id) {

        return menuItemService.getById(id);

    }

    @PutMapping("/{id}")
    public MenuItemResponse update(

            @PathVariable Long id,

            @Valid @RequestBody MenuItemRequest request) {

        return menuItemService.update(id, request);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        menuItemService.delete(id);

    }

}