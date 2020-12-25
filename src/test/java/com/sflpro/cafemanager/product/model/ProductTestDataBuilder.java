package com.sflpro.cafemanager.product.model;

import com.sflpro.cafemanager.product.domain.entity.Product;

import java.math.BigDecimal;

public class ProductTestDataBuilder {
    private long id;
    private String name;
    private BigDecimal price;

    private ProductTestDataBuilder() {

    }

    public static ProductTestDataBuilder aProduct() {
        return new ProductTestDataBuilder();
    }

    public static ProductTestDataBuilder aValidProduct() {
        return aProduct()
                .withId(1)
                .withName("Cola")
                .withPrice(new BigDecimal("5.00"));
    }

    public ProductTestDataBuilder withId(long id) {
        this.id = id;

        return this;
    }

    public ProductTestDataBuilder withName(String name) {
        this.name = name;

        return this;
    }

    public ProductTestDataBuilder withPrice(BigDecimal price) {
        this.price = price;

        return this;
    }

    public Product build() {
        return new Product()
                .setId(id)
                .setName(name)
                .setPrice(price);
    }
}
