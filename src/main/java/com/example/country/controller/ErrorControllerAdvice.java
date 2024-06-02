package com.example.country.controller;

import com.example.country.exception.IllegalOperationException;
import com.example.country.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = {"com.example.country.controller"})
public class ErrorControllerAdvice {

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<Object> handleIllegalOperationException(IllegalOperationException ex){
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ex.getMessageSummary());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<Object> handleServiceRuntimeException(ServiceRuntimeException ex){
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ex.getMessageSummary());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex){
        String message = "An unexpected exception was encountered";
        log.error(String.format("%s : %s", message, ex.getMessage()));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }
}
