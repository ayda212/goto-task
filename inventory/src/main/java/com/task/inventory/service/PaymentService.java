package com.task.inventory.service;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.dto.payment.PaymentDto;
import com.task.inventory.entity.Payment;
import com.task.inventory.exceptions.GeneralException;
import com.task.inventory.repository.PaymentRepo;
import com.task.inventory.repository.PaymentTypeRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class PaymentService {
    final String RESPONSE_CODE_SUCCESS = "0000";

    private final PaymentRepo paymentRepo;
    private final PaymentTypeRepo paymentTypeRepo;
    private final ModelMapper modelMapper;

    final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(PaymentRepo paymentRepo, PaymentTypeRepo paymentTypeRepo, ModelMapper modelMapper) {
        this.paymentRepo = paymentRepo;
        this.paymentTypeRepo = paymentTypeRepo;
        this.modelMapper = modelMapper;
    }

    @CacheEvict(value = "payments",allEntries = true)
    public BaseResponse doSave(PaymentDto request){
        var paymentType = paymentTypeRepo.findByTypeName(request.getPaymentType().getTypeName()).orElseThrow(()->new GeneralException("Payment Type Not Found",HttpStatus.NOT_FOUND));
        var payment = modelMapper.map(request,Payment.class);
        payment.setDate(ZonedDateTime.now());
        payment.setPaymentType(paymentType);
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success save",
                modelMapper.map(paymentRepo.save(payment), PaymentDto.class)
        );
    }
    @CacheEvict(value = "payments",allEntries = true)
    public BaseResponse doUpdate(PaymentDto request){
        var payment = paymentRepo.findById(request.getPaymentId()).orElseThrow(()->new GeneralException("Payment Not Found",HttpStatus.NOT_FOUND));
        var paymentType = paymentTypeRepo.findByTypeName(request.getPaymentType().getTypeName()).orElseThrow(()->new GeneralException("Payment Type Not Found",HttpStatus.NOT_FOUND));
        payment.setPaymentType(paymentType);
        payment.setAmount(request.getAmount());
        payment.setCustomerId(request.getCustomerId());
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Update",
                modelMapper.map(paymentRepo.save(payment), PaymentDto.class)
        );
    }
    public BaseResponse doGetById(Integer paymentId){
        var payment = paymentRepo.findById(paymentId).orElseThrow(()-> new GeneralException("Payment  not found with Id "+paymentId,HttpStatus.NOT_FOUND));
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Get",
                modelMapper.map(paymentRepo.save(payment), PaymentDto.class)
        );
    }
    @Cacheable(value = "payments",keyGenerator = "customKeyGenerator")
    public Page<Payment> doGetByFilter(String customerId, String typeName, Double amount, Pageable pageable){
        logger.info("do get data from database");
        return paymentRepo.findByCustomerIdAndTypeNameAndAmount(customerId,typeName,amount,pageable);
    }
    @CacheEvict(value = "payments",allEntries = true)
    public BaseResponse doDelete(Integer paymentId){
        var payment = paymentRepo.findById(paymentId).orElseThrow(()-> new GeneralException("Payment not found with Id "+paymentId,HttpStatus.NOT_FOUND));
        paymentRepo.deleteById(paymentId);
        return new BaseResponse(
                RESPONSE_CODE_SUCCESS,
                "Success Delete",
                payment
        );
    }
}
