package com.prashant.restaurantpos.table.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.table.dto.TableRequest;
import com.prashant.restaurantpos.table.dto.TableResponse;
import com.prashant.restaurantpos.table.entity.RestaurantTable;
import com.prashant.restaurantpos.table.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {

    private final RestaurantTableRepository tableRepository;

    @Override
    public TableResponse create(TableRequest request) {

        if (tableRepository.existsByTableNumber(request.getTableNumber())) {
            throw new RuntimeException("Table already exists");
        }

        RestaurantTable table = RestaurantTable.builder()
                .tableNumber(request.getTableNumber())
                .capacity(request.getCapacity())
                .build();

        table = tableRepository.save(table);

        return mapToResponse(table);
    }

    @Override
    public List<TableResponse> getAll() {

        return tableRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TableResponse getById(Long id) {

        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        return mapToResponse(table);
    }

    @Override
    public TableResponse update(Long id, TableRequest request) {

        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());

        tableRepository.save(table);

        return mapToResponse(table);
    }

    @Override
    public void delete(Long id) {

        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        tableRepository.delete(table);
    }

    private TableResponse mapToResponse(RestaurantTable table) {

        return TableResponse.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .status(table.getStatus())
                .build();
    }
}
