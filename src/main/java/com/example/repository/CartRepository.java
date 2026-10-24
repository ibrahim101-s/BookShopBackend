package com.example.repository;
import com.example.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserUsername(String username);
}
