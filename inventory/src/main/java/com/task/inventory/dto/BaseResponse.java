package com.task.inventory.dto;

public record BaseResponse(
        String responseCode,
        String responseMessage,
        Object data
) {

}
