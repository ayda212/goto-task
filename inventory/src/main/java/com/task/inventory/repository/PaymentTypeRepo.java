package com.task.inventory.repository;

import com.task.inventory.entity.PaymentType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentTypeRepo extends CrudRepository<PaymentType,Integer> {
    boolean existsByTypeName(String typeName);
    Optional<PaymentType> findByTypeName(String typeName);
}
