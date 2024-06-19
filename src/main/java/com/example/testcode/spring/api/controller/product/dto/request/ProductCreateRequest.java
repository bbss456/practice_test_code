package com.example.testcode.spring.api.controller.product.dto.request;

import com.example.testcode.spring.domain.product.Product;
import com.example.testcode.spring.domain.product.ProductSellingStatus;
import com.example.testcode.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private String name;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private int price;

    @Builder
    private ProductCreateRequest(String name, ProductType type, ProductSellingStatus sellingStatus, int price) {
        this.name = name;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.price = price;
    }

    public Product toEntity(String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .price(price)
                .sellingStatus(sellingStatus)
                .name(name)
                .build();
    }

}
