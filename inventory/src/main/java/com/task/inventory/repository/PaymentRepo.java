package com.task.inventory.repository;

import com.task.inventory.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {
    @Query("select p from Payment p where p.customerId = :customerId and p.paymentType.typeName = :typeName and (:amount is null or p.amount = :amount)")
    Page<Payment> findByCustomerIdAndTypeNameAndAmount(
            @RequestParam("customerId") String customerId,
            @RequestParam("typeName") String typeName,
            @RequestParam("amount") Double amount,
            Pageable pageable
            );
}
