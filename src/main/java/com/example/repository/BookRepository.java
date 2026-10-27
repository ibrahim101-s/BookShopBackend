package com.example.repository;

import com.example.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

    Page<BookEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByBarcode(String barcode);


    boolean existsByBarcodeAndIdNot(String barcode, Long id);
}