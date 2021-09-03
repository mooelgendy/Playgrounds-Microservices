package com.elgendy.invitationservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationException> handleException (ApplicationException e){
        e.setDate(new Date());
        return new ResponseEntity<>(e, e.getHttpStatus());
    }
}
