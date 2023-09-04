package com.task.inventory.controller;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.dto.paymenttype.PaymentTypeRequest;
import com.task.inventory.service.PaymentTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment-type")
public class PaymentTypeController {
    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> doSave(@RequestBody @Valid PaymentTypeRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentTypeService.doSave(request));
    }
    @PutMapping
    public ResponseEntity<BaseResponse> doUpdate(@RequestBody @Valid PaymentTypeRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(paymentTypeService.doUpdate(request));
    }
    @GetMapping("/{paymentTypeId}")
    public ResponseEntity<BaseResponse> doGetById(@PathVariable Integer paymentTypeId){
        return ResponseEntity.status(HttpStatus.OK).body(paymentTypeService.doGetById(paymentTypeId));
    }
    @DeleteMapping("/{paymentTypeId}")
    public ResponseEntity<BaseResponse> doDelete(@PathVariable Integer paymentTypeId){
        return ResponseEntity.status(HttpStatus.OK).body(paymentTypeService.doDelete(paymentTypeId));
    }
}
