package com.sadmansakib.tictactoe.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsertMovesDto {
    private String game_ID;
    private String[] moves;
}
