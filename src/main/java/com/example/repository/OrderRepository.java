package com.example.repository;
import com.example.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserUsernameOrderByCreatedAtDesc(String username);
}
