package com.sflpro.cafemanager.product;

import com.sflpro.cafemanager.AbstractSpringIntegrationTest;
import com.sflpro.cafemanager.product.controller.model.request.CreatProductRequest;
import com.sflpro.cafemanager.product.domain.entity.Product;
import com.sflpro.cafemanager.product.domain.model.ProductModel;
import com.sflpro.cafemanager.product.model.ProductTestDataBuilder;
import com.sflpro.cafemanager.product.repository.ProductRepository;
import com.sflpro.cafemanager.security.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductIT extends AbstractSpringIntegrationTest {
    public static final String CREATE_PRODUCT_URL = "/products";

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = UserRole.MANAGER_ROLE)
    void shouldCreateProductAndReturnMappedProductInResponse() throws Exception {
        Product product = ProductTestDataBuilder.aValidProduct()
                .build();

        CreatProductRequest request = new CreatProductRequest()
                .setName(product.getName())
                .setPrice(product.getPrice());

        String requestJson = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(post(CREATE_PRODUCT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProductModel actualProductModel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductModel.class);

        assertThat(actualProductModel.getId()).isNotNull().isGreaterThan(0);
        assertThat(actualProductModel.getName()).isEqualTo(request.getName());
        assertThat(actualProductModel.getPrice()).isEqualTo(request.getPrice());

        List<Product> dbPriProducts = productRepository.findAll();

        assertThat(dbPriProducts).hasSize(1);

        Product dbProduct = dbPriProducts.get(0);

        assertThat(dbProduct.getId()).isNotNull().isGreaterThan(0);
        assertThat(dbProduct.getName()).isEqualTo(request.getName());
        assertThat(dbProduct.getPrice()).isEqualTo(request.getPrice());
    }
}