package com.example.testcode.spring.api.service.product.response;

import com.example.testcode.spring.domain.product.Product;
import com.example.testcode.spring.domain.product.ProductSellingStatus;
import com.example.testcode.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private String productNumber;
    private String name;
    private ProductType type;
    private ProductSellingStatus sellingType;
    private int price;


    @Builder
    private ProductResponse(Long id,
                            String productNumber,
                            String name,
                            ProductType type,
                            ProductSellingStatus sellingType,
                            int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.name = name;
        this.type = type;
        this.sellingType = sellingType;
        this.price = price;
    }

    public static  ProductResponse of(Product product)  {
        return ProductResponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .type(product.getType())
                .sellingType(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
