package com.sflpro.cafemanager.product.repository;

import com.sflpro.cafemanager.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByName(String name);

    boolean existsProductByName(String username);
}
