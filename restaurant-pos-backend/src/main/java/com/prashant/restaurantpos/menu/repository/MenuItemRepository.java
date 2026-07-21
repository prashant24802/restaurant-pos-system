package com.prashant.restaurantpos.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prashant.restaurantpos.menu.entity.MenuItem;

public interface MenuItemRepository extends
        JpaRepository<MenuItem, Long>,
        JpaSpecificationExecutor<MenuItem> {

}