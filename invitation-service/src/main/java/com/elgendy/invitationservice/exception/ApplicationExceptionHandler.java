package com.elgendy.invitationservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationException> handleException (ApplicationException e){
        return new ResponseEntity<>(e, e.getHttpStatus());
    }
}
