package com.prashant.restaurantpos.table.service;

import java.util.List;

import com.prashant.restaurantpos.table.dto.TableRequest;
import com.prashant.restaurantpos.table.dto.TableResponse;

public interface RestaurantTableService {

    TableResponse create(TableRequest request);

    List<TableResponse> getAll();

    TableResponse getById(Long id);

    TableResponse update(Long id, TableRequest request);

    void delete(Long id);
}