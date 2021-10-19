package com.example.micro.productservice.controller;

import com.example.micro.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/test")
@RequiredArgsConstructor
@RefreshScope
public class TestController {

    @Value("${test.name}")
    private String name;

    @GetMapping
    public String test() {
        return name;
    }
}
