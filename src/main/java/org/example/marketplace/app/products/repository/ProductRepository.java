package org.example.marketplace.app.products.repository;

import org.example.marketplace.app.products.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
