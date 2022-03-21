package com.sadmansakib.tictactoe.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiExceptionPayload {
    private final HttpStatus error;
    private final int errorCode;
    private final String message;
    private final Throwable throwable;
    private final LocalDateTime timeStamp;
}
