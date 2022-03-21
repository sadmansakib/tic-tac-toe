package com.sadmansakib.tictactoe.entity.dto;

import com.sadmansakib.tictactoe.entity.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicTacToeDto {
    private String game_ID;
    private int boardSize;
    private GameStatus gameStatus;
    private String winner;
}
