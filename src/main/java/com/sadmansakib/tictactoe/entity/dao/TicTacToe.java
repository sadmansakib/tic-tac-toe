package com.sadmansakib.tictactoe.entity.dao;

import com.sadmansakib.tictactoe.entity.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicTacToe {
    @Id
    private String ID;
    private GameStatus gameStatus;
    private int size;
    private String winner;
    private String[] moves;

    public TicTacToe(GameStatus gameStatus, int size) {
        this.gameStatus = gameStatus;
        this.size = size;
    }

}


