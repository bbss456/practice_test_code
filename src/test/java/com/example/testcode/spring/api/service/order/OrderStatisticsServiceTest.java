package com.example.testcode.spring.api.service.order;

import com.example.testcode.spring.client.mail.MailSendClient;
import com.example.testcode.spring.domain.history.MailSendHistory;
import com.example.testcode.spring.domain.history.MailSendHistoryRepository;
import com.example.testcode.spring.domain.order.Order;
import com.example.testcode.spring.domain.order.OrderRepository;
import com.example.testcode.spring.domain.order.OrderStatus;
import com.example.testcode.spring.domain.orderproduct.OrderProductRepository;
import com.example.testcode.spring.domain.product.Product;
import com.example.testcode.spring.domain.product.ProductRepository;
import com.example.testcode.spring.domain.product.ProductType;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.testcode.spring.domain.product.ProductSellingStatus.SELLING;
import static com.example.testcode.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 3, 5, 10, 0);

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        List<Product> products = List.of(product1, product2, product3);

        Order order1 = createPaymentCompleteOrder(products, LocalDateTime.of(2023, 3, 4, 23, 59, 59));
        Order order2 = createPaymentCompleteOrder(products, now);
        Order order3 = createPaymentCompleteOrder(products, LocalDateTime.of(2023, 3, 5, 23, 59, 59));
        Order order4 = createPaymentCompleteOrder(products, LocalDateTime.of(2023, 3, 6, 0, 0));

        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        // when
        boolean result =orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 18000원입니다.");
    }

    private Order createPaymentCompleteOrder(List<Product> products, LocalDateTime now) {
        Order order1 = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();

        return orderRepository.save(order1);
    }

    private Product createProduct(ProductType productType, String productNumber, int price) {
        return  Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}