package com.task.inventory.dto.payment;

import com.task.inventory.dto.paymenttype.PaymentTypeRequest;
import com.task.inventory.entity.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.ZonedDateTime;
@Valid
public class PaymentDto {
    @NotEmpty(message = "paymentId not null")
    private Integer paymentId;
    private double amount;
    private @Valid PaymentTypeRequest paymentType;
    private ZonedDateTime date;
    @NotEmpty(message = "customerId not null")
    private String customerId;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentTypeRequest getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeRequest paymentType) {
        this.paymentType = paymentType;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
