package com.sadmansakib.tictactoe.entity.mappings;

import com.sadmansakib.tictactoe.entity.dao.TicTacToe;
import com.sadmansakib.tictactoe.entity.dto.TicTacToeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mappings({
            @Mapping(source = "ID", target = "game_ID"),
            @Mapping(source = "size", target = "boardSize"),
            @Mapping(source = "gameStatus", target = "gameStatus"),
            @Mapping(source = "winner", target = "winner")
    })
    TicTacToeDto ticTacToeToDto(TicTacToe ticTacToe);
}
