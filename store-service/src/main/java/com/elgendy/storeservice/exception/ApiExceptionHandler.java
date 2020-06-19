package com.elgendy.storeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException (ApiNotFoundException e){
        ApiException apiException = new ApiException(e.getMessage(), e, httpStatus, new Date());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
