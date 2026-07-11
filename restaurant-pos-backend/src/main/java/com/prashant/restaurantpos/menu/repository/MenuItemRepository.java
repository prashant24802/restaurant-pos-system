package com.prashant.restaurantpos.menu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prashant.restaurantpos.menu.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("""
        SELECT m
        FROM MenuItem m
        WHERE (:search IS NULL
            OR :search = ''
            OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(m.description) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(m.category.name) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<MenuItem> search(
            @Param("search") String search,
            Pageable pageable);

    Page<MenuItem> findByCategoryId(
            Long categoryId,
            Pageable pageable);
}