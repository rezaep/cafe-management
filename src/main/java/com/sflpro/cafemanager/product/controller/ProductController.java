package com.sflpro.cafemanager.product.controller;

import com.sflpro.cafemanager.product.controller.model.request.CreatProductRequest;
import com.sflpro.cafemanager.product.domain.model.ProductModel;
import com.sflpro.cafemanager.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductModel createProduct(@Valid @RequestBody CreatProductRequest request) {
        return productService.createProduct(request.getName(), request.getPrice());
    }
}
