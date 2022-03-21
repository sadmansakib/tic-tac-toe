package com.sadmansakib.tictactoe.controller;

import com.sadmansakib.tictactoe.entity.dto.GameCreateDto;
import com.sadmansakib.tictactoe.entity.dto.InsertMovesDto;
import com.sadmansakib.tictactoe.entity.dto.TicTacToeDto;
import com.sadmansakib.tictactoe.service.TicTacToeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tictactoe")
public class TicTacToeController {
    private final TicTacToeService ticTacToeService;

    @Autowired
    public TicTacToeController(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }

    @PostMapping("/create")
    public ResponseEntity<TicTacToeDto> createANewTicTacToeGame(@RequestBody GameCreateDto gameCreateDto){
        TicTacToeDto createdGame = ticTacToeService.createANewGame(gameCreateDto);
        return new ResponseEntity<>(createdGame, HttpStatus.OK);
    }

    @PostMapping("/get-winner")
    public ResponseEntity<TicTacToeDto> getWinnerOfTicTacToeGame(@RequestBody InsertMovesDto insertMovesDto){
        TicTacToeDto updatedGame = ticTacToeService.determineWinnerOfTheGame(insertMovesDto);
        return new ResponseEntity<>(updatedGame,HttpStatus.OK);
    }
}
