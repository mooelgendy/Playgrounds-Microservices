package com.elgendy.playgroundservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Translates exceptions into the stable {@link ApiError} payload.
 *
 * <p>The old handler only knew about {@link ApiNotFoundException} and exposed
 * the raw exception (including its cause) in the response. Everything else,
 * including validation failures, fell through to a generic {@code 500}.</p>
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ApiNotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, exception.getMessage(), request, Collections.emptyList());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(InvalidRequestException exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Collections.emptyList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleBodyValidation(MethodArgumentNotValidException exception,
                                                         HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Request validation failed", request,
                fieldErrors(exception.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBinding(BindException exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Request validation failed", request,
                fieldErrors(exception.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException exception,
                                                              HttpServletRequest request) {
        List<ApiError.FieldError> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errors.add(new ApiError.FieldError(lastNode(violation), violation.getMessage()));
        }
        return build(HttpStatus.BAD_REQUEST, "Request validation failed", request, errors);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> handleUnreadableRequest(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Malformed request", request, Collections.emptyList());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception,
                                                           HttpServletRequest request) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage(), request, Collections.emptyList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception exception, HttpServletRequest request) {
        log.error("Unhandled error while processing {} {}", request.getMethod(), request.getRequestURI(), exception);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", request, Collections.emptyList());
    }

    private static List<ApiError.FieldError> fieldErrors(BindingResult bindingResult) {
        List<ApiError.FieldError> errors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(new ApiError.FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return errors;
    }

    private static String lastNode(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        int dot = path.lastIndexOf('.');
        return dot >= 0 ? path.substring(dot + 1) : path;
    }

    private static ResponseEntity<ApiError> build(HttpStatus status, String message, HttpServletRequest request,
                                                  List<ApiError.FieldError> fieldErrors) {
        ApiError body = new ApiError(status, message, request.getRequestURI(), fieldErrors);
        return ResponseEntity.status(status).body(body);
    }
}
