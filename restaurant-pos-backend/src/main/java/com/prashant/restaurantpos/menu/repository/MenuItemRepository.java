package com.prashant.restaurantpos.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prashant.restaurantpos.menu.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("""
        SELECT m
        FROM MenuItem m
        WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(m.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<MenuItem> search(@Param("keyword") String keyword);

    List<MenuItem> findByCategoryId(Long categoryId);
}