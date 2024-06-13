package com.example.testcode.spring.api.service.order;

import com.example.testcode.spring.api.service.order.request.OrderCreateRequest;
import com.example.testcode.spring.api.service.order.response.OrderResponse;
import com.example.testcode.spring.domain.order.Order;
import com.example.testcode.spring.domain.order.OrderRepository;
import com.example.testcode.spring.domain.product.Product;
import com.example.testcode.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> duplicateProducts = productNumbers
                .stream()
                .map(productMap::get).collect(Collectors.toList());

        Order order =  Order.create(duplicateProducts, registeredDateTime);
        Order saveOrder = orderRepository.save(order);

        return OrderResponse.of(saveOrder);
    }
}
