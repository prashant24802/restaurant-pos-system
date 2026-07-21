package com.prashant.restaurantpos.menu.specification;

import org.springframework.data.jpa.domain.Specification;

import com.prashant.restaurantpos.menu.entity.MenuItem;

public class MenuItemSpecification {

    private MenuItemSpecification() {
    }

    public static Specification<MenuItem> filter(
            String search,
            Long categoryId,
            Boolean available) {

        return Specification
                .where(hasSearch(search))
                .and(hasCategory(categoryId))
                .and(hasAvailability(available));

    }

    private static Specification<MenuItem> hasSearch(String search) {

        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }

            String keyword = "%" + search.toLowerCase() + "%";

            return cb.or(

                    cb.like(
                            cb.lower(root.get("name")),
                            keyword),

                    cb.like(
                            cb.lower(root.get("description")),
                            keyword),

                    cb.like(
                            cb.lower(root.get("category").get("name")),
                            keyword));

        };

    }

    private static Specification<MenuItem> hasCategory(Long categoryId) {

        return (root, query, cb) -> {

            if (categoryId == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("category").get("id"),
                    categoryId);

        };

    }

    private static Specification<MenuItem> hasAvailability(Boolean available) {

        return (root, query, cb) -> {

            if (available == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("available"),
                    available);

        };

    }

}