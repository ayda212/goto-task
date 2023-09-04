package com.task.inventory.service;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.dto.inventory.InventoryDto;
import com.task.inventory.dto.payment.PaymentDto;
import com.task.inventory.entity.Inventory;
import com.task.inventory.exceptions.GeneralException;
import com.task.inventory.repository.InventoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {
    final String RESPONSE_CODE_SUCCESS = "0000";
    private final ModelMapper modelMapper;
    private final InventoryRepo inventoryRepo;

    public InventoryService(ModelMapper modelMapper, InventoryRepo inventoryRepo) {
        this.modelMapper = modelMapper;
        this.inventoryRepo = inventoryRepo;
    }


    public BaseResponse doSave(InventoryDto request){
        var inventory = modelMapper.map(request, Inventory.class);
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success save",
                modelMapper.map(inventoryRepo.save(inventory), InventoryDto.class)
        );
    }
    public BaseResponse doUpdate(InventoryDto request){
        var inventory = inventoryRepo.findById(request.getItemId()).orElseThrow(()->new GeneralException("inventory not found",HttpStatus.NOT_FOUND));
        inventory.setPrice(request.getPrice());
        inventory.setQuantity(request.getQuantity());
        inventory.setItemName(request.getItemName());
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Update",
                modelMapper.map(inventoryRepo.save(inventory), InventoryDto.class)
        );
    }
    public BaseResponse doGetById(Integer itemId){
        var inventory = inventoryRepo.findById(itemId).orElseThrow(()-> new GeneralException("Inventory not found with Id "+itemId,HttpStatus.NOT_FOUND));
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Get",
                modelMapper.map(inventoryRepo.save(inventory), InventoryDto.class)
        );
    }
    public BaseResponse doDelete(Integer itemId){
        var inventory = inventoryRepo.findById(itemId).orElseThrow(()-> new GeneralException("inventory not found with Id "+itemId,HttpStatus.NOT_FOUND));
        inventoryRepo.deleteById(itemId);
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Delete",
                inventory
        );
    }
}
