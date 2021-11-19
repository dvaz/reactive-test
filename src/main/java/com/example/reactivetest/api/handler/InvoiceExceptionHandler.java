package com.example.reactivetest.api.handler;

import com.example.reactivetest.exception.InvoiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvoiceExceptionHandler {

    @ExceptionHandler(value = {InvoiceNotFoundException.class})
    public ResponseEntity<ErrorMessage> handlerInvoiceNotFoundException(InvoiceNotFoundException e){
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage() == null ? e.toString() : e.getMessage()));

    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handlerException(Exception e){
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(e.getMessage() == null ? e.toString() : e.getMessage()));

    }
}
