package com.example.micro.orderservice.controller;

import com.example.micro.orderservice.client.InventoryClient;
import com.example.micro.orderservice.model.Order;
import com.example.micro.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    private Boolean handleErrorCase() {
        return false;
    }
}