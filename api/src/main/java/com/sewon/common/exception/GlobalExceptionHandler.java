package com.sewon.common.exception;

import com.sewon.common.response.ApiErrorResponse;
import com.sewon.security.exception.AuthErrorCode;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> domainExceptionHandler(DomainException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(badRequest.value(),
            ex.getErrorCode());

        return ResponseEntity.status(badRequest).body(apiErrorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> constraintViolationExceptionHandler(
        ConstraintViolationException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(badRequest.value(),
            InternalErrorCode.CONSTRAINT_VIOLATION);
        log.error("ex message: {}", ex.getMessage());
        return ResponseEntity.status(badRequest).body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> methodArgumentNotValidExceptionHandler(
        MethodArgumentNotValidException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(badRequest.value(),
            InternalErrorCode.INVALID_METHOD_VALUE);
        log.error("ex message: {}", ex.getMessage());
        return ResponseEntity.status(badRequest).body(apiErrorResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiErrorResponse> jwtExceptionHandler(JwtException ex) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(forbidden.value(),
            AuthErrorCode.UNAUTHENTICATED_ACCESS);
        log.error("ex message: {}", ex.getMessage());
        return ResponseEntity.status(forbidden).body(apiErrorResponse);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(internalServerError.value(),
            InternalErrorCode.INTERNAL_ERROR);
        log.error("ex cause: {}",
            ex.getCause() != null ? ex.getCause().toString() : "ex.cause is null");
        log.error("ex message: {}", ex.getMessage());
        return ResponseEntity.status(internalServerError).body(apiErrorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Void> ioexceptionHandler(IOException ex) {
        log.error("io ex cause: {}",
            ex.getCause() != null ? ex.getCause().toString() : "ex.cause is null");
        log.error("io ex message: {}", ex.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<Void> asyncRequestTimeoutExceptionHandler(
        AsyncRequestTimeoutException ex) {
        log.error("ex cause: {}",
            ex.getCause() != null ? ex.getCause().toString() : "ex.cause is null");
        log.error("ex message: {}", ex.getMessage());
        return ResponseEntity.internalServerError().build();
    }


}
