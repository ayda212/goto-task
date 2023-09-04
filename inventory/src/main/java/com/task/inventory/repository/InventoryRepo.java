package com.task.inventory.repository;

import com.task.inventory.entity.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepo extends CrudRepository<Inventory,Integer> {
}
