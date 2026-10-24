package com.example.specification;
import com.example.entity.BookEntity;
import org.springframework.data.jpa.domain.Specification;
public final class BookSpecification {
    private BookSpecification() {}
    public static Specification<BookEntity> filter(String name, String author) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
            if (name != null && !name.isBlank()) predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%"));
            if (author != null && !author.isBlank()) predicate = cb.and(predicate, cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase().trim() + "%"));
            return predicate;
        };
    }
}
