package com.prashant.restaurantpos.table.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prashant.restaurantpos.table.dto.TableRequest;
import com.prashant.restaurantpos.table.dto.TableResponse;
import com.prashant.restaurantpos.table.entity.TableStatus;

public interface RestaurantTableService {

    TableResponse create(TableRequest request);

    Page<TableResponse> getPage(
            String search,
            TableStatus status,
            Pageable pageable);

    TableResponse getById(Long id);

    TableResponse update(
            Long id,
            TableRequest request);

    void delete(Long id);

}