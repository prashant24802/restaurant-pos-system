package com.prashant.restaurantpos.menu.controller;

import java.util.List;

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
    public MenuItemResponse create(@Valid @RequestBody MenuItemRequest request) {
        return menuItemService.create(request);
    }

    @GetMapping
    public List<MenuItemResponse> getAll() {
        return menuItemService.getAll();
    }

    @GetMapping("/{id}")
    public MenuItemResponse getById(@PathVariable Long id) {
        return menuItemService.getById(id);
    }

    @GetMapping("/search")
    public List<MenuItemResponse> search(@RequestParam String keyword) {
        return menuItemService.search(keyword);
    }

    @PutMapping("/{id}")
    public MenuItemResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemRequest request) {

        return menuItemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuItemService.delete(id);
    }
}