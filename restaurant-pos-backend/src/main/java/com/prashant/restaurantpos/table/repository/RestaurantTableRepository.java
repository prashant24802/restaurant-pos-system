package com.prashant.restaurantpos.table.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.restaurantpos.table.entity.RestaurantTable;

public interface RestaurantTableRepository
        extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByTableNumber(Integer tableNumber);

    boolean existsByTableNumber(Integer tableNumber);
}