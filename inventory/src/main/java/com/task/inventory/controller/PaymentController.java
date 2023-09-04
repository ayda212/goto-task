package com.task.inventory.controller;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.dto.payment.PaymentDto;
import com.task.inventory.entity.Payment;
import com.task.inventory.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> doSave(@RequestBody @Valid PaymentDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.doSave(request));
    }
    @PutMapping
    public ResponseEntity<BaseResponse> doUpdate(@RequestBody @Valid PaymentDto request){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.doUpdate(request));
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<BaseResponse> doGetById(@PathVariable Integer paymentId){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.doGetById(paymentId));
    }
    @CrossOrigin
    @GetMapping()
    public Page<Payment> doGetByFilter(
            @RequestParam String customerId,    //mandatory
            @RequestParam String typeName,      //mandatory
            @RequestParam(required = false) Double amount,         //non mandatory
            Pageable pageable
            ){
        return paymentService.doGetByFilter(customerId,typeName,amount,pageable);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<BaseResponse> doDelete(@PathVariable Integer paymentId){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.doDelete(paymentId));
    }

}
