package com.prashant.restaurantpos.table.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.table.dto.TableRequest;
import com.prashant.restaurantpos.table.dto.TableResponse;
import com.prashant.restaurantpos.table.entity.RestaurantTable;
import com.prashant.restaurantpos.table.entity.TableStatus;
import com.prashant.restaurantpos.table.repository.RestaurantTableRepository;
import com.prashant.restaurantpos.table.specification.RestaurantTableSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl
        implements RestaurantTableService {

    private final RestaurantTableRepository tableRepository;

    @Override
    public TableResponse create(
            TableRequest request) {

        if (tableRepository.existsByTableNumber(
                request.getTableNumber())) {

            throw new RuntimeException(
                    "Table already exists");

        }

        RestaurantTable table =
                RestaurantTable.builder()
                        .tableNumber(
                                request.getTableNumber())
                        .capacity(
                                request.getCapacity())
                        .build();

        table = tableRepository.save(table);

        return mapToResponse(table);

    }

    @Override
    public Page<TableResponse> getPage(
            String search,
            TableStatus status,
            Pageable pageable) {

        return tableRepository.findAll(

                RestaurantTableSpecification.filter(
                        search,
                        status),

                pageable)

                .map(this::mapToResponse);

    }

    @Override
    public TableResponse getById(
            Long id) {

        RestaurantTable table =
                tableRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Table not found"));

        return mapToResponse(table);

    }

    @Override
    public TableResponse update(
            Long id,
            TableRequest request) {

        RestaurantTable table =
                tableRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Table not found"));

        if (!table.getTableNumber()
                .equals(request.getTableNumber())
                && tableRepository.existsByTableNumber(
                        request.getTableNumber())) {

            throw new RuntimeException(
                    "Table number already exists");

        }

        table.setTableNumber(
                request.getTableNumber());

        table.setCapacity(
                request.getCapacity());

        table = tableRepository.save(table);

        return mapToResponse(table);

    }

    @Override
    public void delete(
            Long id) {

        RestaurantTable table =
                tableRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Table not found"));

        tableRepository.delete(table);

    }

    private TableResponse mapToResponse(
            RestaurantTable table) {

        return TableResponse.builder()

                .id(table.getId())

                .tableNumber(
                        table.getTableNumber())

                .capacity(
                        table.getCapacity())

                .status(
                        table.getStatus())

                .build();

    }

}