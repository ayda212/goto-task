package com.task.inventory.service;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.dto.paymenttype.PaymentTypeRequest;
import com.task.inventory.dto.paymenttype.PaymentTypeResponse;
import com.task.inventory.entity.PaymentType;
import com.task.inventory.exceptions.GeneralException;
import com.task.inventory.repository.PaymentTypeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService {
    private final PaymentTypeRepo paymentTypeRepo;
    private final ModelMapper modelMapper;
    final String RESPONSE_CODE_SUCCESS = "0000";

    public PaymentTypeService(PaymentTypeRepo paymentTypeRepo, ModelMapper modelMapper) {
        this.paymentTypeRepo = paymentTypeRepo;
        this.modelMapper = modelMapper;
    }

    public BaseResponse doSave(PaymentTypeRequest request){
        if(paymentTypeRepo.existsByTypeName(request.getTypeName())){
            throw new GeneralException("typeName Already Exist", HttpStatus.CONFLICT);
        }
        var paymentType = new PaymentType(request.getTypeName());
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success save",
                modelMapper.map(paymentTypeRepo.save(paymentType), PaymentTypeResponse.class)
        );
    }
    public BaseResponse doUpdate(PaymentTypeRequest request){
        var paymentType = paymentTypeRepo.findById(request.getPaymentTypeId()).orElseThrow(()->new GeneralException("Payment Type Not Found",HttpStatus.NOT_FOUND));
        paymentType.setTypeName(request.getTypeName());
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Update",
                modelMapper.map(paymentTypeRepo.save(paymentType), PaymentTypeResponse.class)
        );
    }
    public BaseResponse doGetById(Integer paymentTypeId){
        var paymentType = paymentTypeRepo.findById(paymentTypeId).orElseThrow(()-> new GeneralException("Payment Type not found with Id "+paymentTypeId,HttpStatus.NOT_FOUND));
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Get",
                modelMapper.map(paymentTypeRepo.save(paymentType), PaymentTypeResponse.class)
        );
    }
    public BaseResponse doDelete(Integer paymentTypeId){
        var paymentType = paymentTypeRepo.findById(paymentTypeId).orElseThrow(()-> new GeneralException("Payment Type not found with Id "+paymentTypeId,HttpStatus.NOT_FOUND));
        paymentTypeRepo.deleteById(paymentTypeId);
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Delete",
                paymentType
        );
    }
}
