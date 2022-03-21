package com.sadmansakib.tictactoe.entity.enums;

public enum GameStatus {
    NEW("new"),
    IN_PROGRESS("in_progress"),
    FINISHED("finished");

    private final String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
