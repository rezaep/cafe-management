package com.sflpro.cafemanager.product.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class CreatProductRequest {
    @NotEmpty
    private String name;
    private BigDecimal price;
}
