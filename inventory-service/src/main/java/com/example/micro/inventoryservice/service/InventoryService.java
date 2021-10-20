package com.example.micro.inventoryservice.service;

import com.example.micro.inventoryservice.model.Inventory;
import com.example.micro.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    public InventoryRepository inventoryRepository;

    public Boolean isInStock(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new RuntimeException("Cannot Find Product by sku code " + skuCode));
        return inventory.getStock() > 0;
    }
}
