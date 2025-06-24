package com.sewon.common.exception;

import com.sewon.common.response.ApiErrorResponse;
import com.sewon.security.exception.AuthErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(internalServerError.value(),
            AuthErrorCode.INTERNAL_ERROR);
        log.error("ex message: {}", ex.getMessage());
        return ResponseEntity.status(internalServerError).body(apiErrorResponse);
    }
}
