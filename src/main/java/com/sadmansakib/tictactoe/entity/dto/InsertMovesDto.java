package com.sadmansakib.tictactoe.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertMovesDto {
    private String game_ID;
    private String[] moves;
}
