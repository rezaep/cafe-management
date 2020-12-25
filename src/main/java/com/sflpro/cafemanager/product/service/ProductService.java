package com.sflpro.cafemanager.product.service;

import com.sflpro.cafemanager.exception.AlreadyExistException;
import com.sflpro.cafemanager.product.domain.entity.Product;
import com.sflpro.cafemanager.product.domain.model.ProductModel;
import com.sflpro.cafemanager.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductModel createProduct(String name, BigDecimal price) {
        if (productRepository.existsProductByName(name)) {
            throw new AlreadyExistException("A product with the given name already exists.");
        }

        Product user = new Product()
                .setName(name)
                .setPrice(price);

        productRepository.save(user);

        return convertToModel(user);
    }

    private ProductModel convertToModel(Product product) {
        return new ProductModel(product.getId(), product.getName(), product.getPrice());
    }
}
