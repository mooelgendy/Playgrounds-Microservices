package com.elgendy.invitationservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ApplicationException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;
    private Throwable throwable;
    private Date date;

    public ApplicationException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
