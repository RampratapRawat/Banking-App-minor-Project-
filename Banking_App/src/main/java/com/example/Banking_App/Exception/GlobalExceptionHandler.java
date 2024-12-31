package com.example.Banking_App.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> handleAccountException(AccountException account, WebRequest webRequest){
        ErrorDetails error=new ErrorDetails(LocalDateTime.now(), account.getMessage(), webRequest.getDescription(false), "Account_Not_Found");
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDetails> handleException(Exception exception,WebRequest webRequest){
           ErrorDetails error=new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), "Internal_Server_Error");
           return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
