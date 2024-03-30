package com.restaurantManagement.backendAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> UserNotFoundException(UserNotFoundException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> NotFoundException(NotFoundException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> RoleAlreadyExistException(RoleAlreadyExistException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> UserAlreadyExistException(UserAlreadyExistException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }
}
