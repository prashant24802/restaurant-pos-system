package com.prashant.restaurantpos.order.specification;

import org.springframework.data.jpa.domain.Specification;

import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderStatus;

public class OrderSpecification {

    private OrderSpecification() {
    }

    public static Specification<Order> filter(
            String search,
            OrderStatus status) {

        return Specification
                .where(hasSearch(search))
                .and(hasStatus(status));

    }

    public static Specification<Order> hasSearch(
            String search) {

        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {

                return null;

            }

            String keyword =
                    "%" + search.trim().toLowerCase() + "%";

            return cb.like(

                    cb.lower(

                            root.get("table")
                                    .get("tableNumber")
                                    .as(String.class)

                    ),

                    keyword

            );

        };

    }

    public static Specification<Order> hasStatus(
            OrderStatus status) {

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