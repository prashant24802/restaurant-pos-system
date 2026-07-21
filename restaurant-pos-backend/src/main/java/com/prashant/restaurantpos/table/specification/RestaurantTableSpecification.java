package com.prashant.restaurantpos.table.specification;

import org.springframework.data.jpa.domain.Specification;

import com.prashant.restaurantpos.table.entity.RestaurantTable;
import com.prashant.restaurantpos.table.entity.TableStatus;

public class RestaurantTableSpecification {

    private RestaurantTableSpecification() {
    }

    public static Specification<RestaurantTable> filter(
            String search,
            TableStatus status) {

        return Specification
                .where(hasSearch(search))
                .and(hasStatus(status));

    }

    public static Specification<RestaurantTable> hasSearch(
            String search) {

        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {

                return null;

            }

            String keyword = "%" + search.trim().toLowerCase() + "%";

            return cb.like(

                    cb.lower(

                            root.get("tableNumber").as(String.class)

                    ),

                    keyword

            );

        };

    }

    public static Specification<RestaurantTable> hasStatus(
            TableStatus status) {

        return (root, query, cb) -> {

            if (status == null) {

                return null;

            }

            return cb.equal(

                    root.get("status"),

                    status

            );

        };

    }

}