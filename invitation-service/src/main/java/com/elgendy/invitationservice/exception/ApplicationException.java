package com.elgendy.invitationservice.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {

    private final String message;
    private final HttpStatusCode httpStatus;
    private final Throwable throwable;
    private final Date date;
}
