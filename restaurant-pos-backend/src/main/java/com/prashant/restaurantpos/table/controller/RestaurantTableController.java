package com.prashant.restaurantpos.table.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.table.dto.TableRequest;
import com.prashant.restaurantpos.table.dto.TableResponse;
import com.prashant.restaurantpos.table.service.RestaurantTableService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService tableService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TableResponse create(@Valid @RequestBody TableRequest request) {
        return tableService.create(request);
    }

    @GetMapping
    public List<TableResponse> getAll() {
        return tableService.getAll();
    }

    @GetMapping("/{id}")
    public TableResponse getById(@PathVariable Long id) {
        return tableService.getById(id);
    }

    @PutMapping("/{id}")
    public TableResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TableRequest request) {

        return tableService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tableService.delete(id);
    }
}