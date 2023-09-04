package com.task.inventory.controller;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.dto.inventory.InventoryDto;
import com.task.inventory.dto.payment.PaymentDto;
import com.task.inventory.service.InventoryService;
import com.task.inventory.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> doSave(@RequestBody @Valid InventoryDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.doSave(request));
    }
    @PutMapping
    public ResponseEntity<BaseResponse> doUpdate(@RequestBody @Valid InventoryDto request){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.doUpdate(request));
    }
    @GetMapping("/{itemId}")
    public ResponseEntity<BaseResponse> doGetById(@PathVariable Integer itemId){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.doGetById(itemId));
    }
    @DeleteMapping("/{itemId}")
    public ResponseEntity<BaseResponse> doDelete(@PathVariable Integer itemId){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.doDelete(itemId));
    }
}
