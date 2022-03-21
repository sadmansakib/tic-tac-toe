package com.sadmansakib.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBoardSizeException extends RuntimeException {
    public InvalidBoardSizeException() {
        super();
    }

    public InvalidBoardSizeException(String message) {
        super(message);
    }

    public InvalidBoardSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBoardSizeException(Throwable cause) {
        super(cause);
    }

    protected InvalidBoardSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
