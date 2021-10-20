package com.example.micro.inventoryservice.repository;

import com.example.micro.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    public Optional<Inventory> findBySkuCode(String skuCode);

}
