package com.sadmansakib.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidBoardSizeException.class})
    public ResponseEntity<Object> handleInvalidBoardSizeException(InvalidBoardSizeException ex) {
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex, LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(
                HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex, LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.NOT_FOUND);
    }
}
