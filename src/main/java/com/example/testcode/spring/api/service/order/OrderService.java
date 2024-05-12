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

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumber = request.getProductNumbers();

        List<Product> products = productRepository.findAllByProductNumberIn(productNumber);

        Order order =  Order.create(products, registeredDateTime);
        Order saveOrder = orderRepository.save(order);

        return OrderResponse.of(saveOrder);
    }
}
