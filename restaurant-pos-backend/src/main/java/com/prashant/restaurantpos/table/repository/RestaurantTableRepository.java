package com.prashant.restaurantpos.table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prashant.restaurantpos.table.entity.RestaurantTable;
import com.prashant.restaurantpos.table.entity.TableStatus;

public interface RestaurantTableRepository
        extends JpaRepository<RestaurantTable, Long>,
        JpaSpecificationExecutor<RestaurantTable> {

    boolean existsByTableNumber(Integer tableNumber);

    long countByStatus(TableStatus status);

}