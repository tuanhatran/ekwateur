package com.ekwateur.invoice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {ClientNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Message handleNotFoundException(RuntimeException exception){
        return new Message(exception.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, ConsumptionDetailInvalidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Message handleExceptionRequest(RuntimeException exception){
        return new Message(exception.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Message handleRequestArgumentNotValid(BindException exception){
        return new Message("Error while trying to valid request: "
                + Objects.requireNonNull(exception.getFieldError().getField())
                + ":" + exception.getLocalizedMessage());
    }

    @ExceptionHandler(value = {InvoiceNotFoundException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Message handleNoInvoiceFound(RuntimeException exception){
        return new Message(exception.getMessage());
    }
}
