package com.prashant.restaurantpos.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.restaurantpos.menu.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByNameContainingIgnoreCase(String keyword);

    List<MenuItem> findByCategoryId(Long categoryId);
}