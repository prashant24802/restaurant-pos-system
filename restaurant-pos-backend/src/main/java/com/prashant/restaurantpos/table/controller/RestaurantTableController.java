package com.prashant.restaurantpos.table.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.restaurantpos.table.dto.TableRequest;
import com.prashant.restaurantpos.table.dto.TableResponse;
import com.prashant.restaurantpos.table.entity.TableStatus;
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
    public TableResponse create(
            @Valid @RequestBody TableRequest request) {

        return tableService.create(request);

    }

    @GetMapping
    public Page<TableResponse> getTables(

            @RequestParam(required = false)
            String search,

            @RequestParam(required = false)
            TableStatus status,

            Pageable pageable) {

        return tableService.getPage(

                search,

                status,

                pageable

        );

    }

    @GetMapping("/{id}")
    public TableResponse getById(
            @PathVariable Long id) {

        return tableService.getById(id);

    }

    @PutMapping("/{id}")
    public TableResponse update(

            @PathVariable Long id,

            @Valid
            @RequestBody TableRequest request) {

        return tableService.update(

                id,

                request

        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        tableService.delete(id);

    }

}