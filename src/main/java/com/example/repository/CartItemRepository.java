package com.example.repository;
import com.example.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartId(Long cartId);
    Optional<CartItemEntity> findByCartIdAndBookId(Long cartId, Long bookId);
}
