package com.example.micro.orderservice.service;

import com.example.micro.orderservice.client.InventoryClient;
import com.example.micro.orderservice.model.Order;
import com.example.micro.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public String save(Order order) {
        Resilience4JCircuitBreaker resilience4JCircuitBreaker = circuitBreakerFactory.create("inventory");
        Supplier<Boolean> booleanSupplier = () -> order.getOrderLineItems().stream()
                .allMatch(lineItem -> inventoryClient.checkStock(lineItem.getSkuCode()));
        Boolean productsInStock = resilience4JCircuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());

        if (productsInStock) {
            log.info("Sending Order Details with Order Id {} to Notification Service", order.getId());
            order.setOrderNumber(UUID.randomUUID().toString());
            orderRepository.save(order);
            return "Order Place Successfully";
        } else {
            return "Order Failed - One of the Product in your Order is out of stock";
        }
    }

    private Boolean handleErrorCase() {
        return false;
    }
}
