package dev.weverton.com.blogapi.controller;

import dev.weverton.com.blogapi.exceptions.BaseException;
import dev.weverton.com.blogapi.exceptions.DuplicatedKeyException;
import dev.weverton.com.blogapi.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseException> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        BaseException exception = new BaseException(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(DuplicatedKeyException.class)
    public ResponseEntity<BaseException> duplicatedKeyException(
            DuplicatedKeyException e, HttpServletRequest request
    ){
        BaseException exception = new BaseException(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Duplicate key error",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

}
