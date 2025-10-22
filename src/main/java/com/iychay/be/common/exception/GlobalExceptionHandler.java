package com.iychay.be.common.exception;

import com.iychay.be.common.dto.ErrorResponse;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidation(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, Exception ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(status).body(response);
    }
}
