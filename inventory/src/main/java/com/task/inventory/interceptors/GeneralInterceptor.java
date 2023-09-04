package com.task.inventory.interceptors;

import com.task.inventory.dto.BaseResponse;
import com.task.inventory.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GeneralInterceptor {
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseResponse> doHandleGeneralException(GeneralException e){
        var baseResponse = new BaseResponse(
                "00XX",
                "Error",
                Map.of("message",e.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> listMessage = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            listMessage.add(errorMessage);
        });

        var baseResponse = new BaseResponse(
                "00XX",
                "Error",
                Map.of("message",listMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }
}
