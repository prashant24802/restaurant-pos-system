package com.prashant.restaurantpos.menu.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.restaurantpos.menu.entity.MenuCategory;

public interface MenuCategoryRepository
        extends JpaRepository<MenuCategory, Long> {

    Optional<MenuCategory> findByName(String name);
}