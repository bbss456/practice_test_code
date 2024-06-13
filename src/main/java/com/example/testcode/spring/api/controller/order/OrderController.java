package com.example.testcode.spring.api.controller.order;

import com.example.testcode.spring.api.service.order.OrderService;
import com.example.testcode.spring.api.service.order.request.OrderCreateRequest;
import com.example.testcode.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
       return orderService.createOrder(request, LocalDateTime.now());
    }
}
