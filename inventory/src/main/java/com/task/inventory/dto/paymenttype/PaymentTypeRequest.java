package com.task.inventory.dto.paymenttype;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Valid
public class PaymentTypeRequest {
    private Integer paymentTypeId;
    @NotEmpty(message = "typeName not null")
    private String typeName;

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
