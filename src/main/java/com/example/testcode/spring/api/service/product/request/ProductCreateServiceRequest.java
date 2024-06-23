package com.example.testcode.spring.api.service.product.request;

import com.example.testcode.spring.domain.product.Product;
import com.example.testcode.spring.domain.product.ProductSellingStatus;
import com.example.testcode.spring.domain.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private String name;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private int price;

    @Builder
    private ProductCreateServiceRequest(String name, ProductType type, ProductSellingStatus sellingStatus, int price) {
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
