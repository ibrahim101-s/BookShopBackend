package com.example.specification;

import com.example.entity.BookEntity;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookEntity> hasName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<BookEntity> hasAuthor(String author) {
        return (root, query, cb) -> author == null ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    public static Specification<BookEntity> priceBetween(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) return cb.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null) return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}