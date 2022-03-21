package com.sadmansakib.tictactoe.service;

import com.sadmansakib.tictactoe.entity.Coordinates;
import com.sadmansakib.tictactoe.entity.dao.TicTacToe;
import com.sadmansakib.tictactoe.entity.dto.GameCreateDto;
import com.sadmansakib.tictactoe.entity.dto.InsertMovesDto;
import com.sadmansakib.tictactoe.entity.dto.TicTacToeDto;
import com.sadmansakib.tictactoe.entity.enums.GameStatus;
import com.sadmansakib.tictactoe.exception.InvalidBoardSizeException;
import com.sadmansakib.tictactoe.exception.NotFoundException;
import com.sadmansakib.tictactoe.repository.TicTocRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class TicTacToeServiceTest {

    @Mock
    private TicTocRepository ticTocRepository;
    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private GameWinnerService gameWinnerService;
    private TicTacToeService ticTacToeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticTacToeService = new TicTacToeService(ticTocRepository, gameWinnerService, mongoTemplate);
    }

    @Test
    void testCreateANewGame() {
        GameCreateDto gameCreateDto = new GameCreateDto(3);
        var actual = ticTacToeService.createANewGame(gameCreateDto);
        assertNotNull(actual);
    }

    @Test
    void testCreateANewGameWithBoardSize12() {
        GameCreateDto gameCreateDto = new GameCreateDto(12);
        var actual = ticTacToeService.createANewGame(gameCreateDto);
        assertNotNull(actual);
    }

    @Test
    void testFailedToCreateANewGame() {
        GameCreateDto gameCreateDto = new GameCreateDto(2);
//        var actual = ticTacToeService.createANewGame(gameCreateDto);
        Exception ex = assertThrows(InvalidBoardSizeException.class, () ->
                ticTacToeService.createANewGame(gameCreateDto));
        String expectedMessage = "Invalid board size was given";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testFailedToCreateANewGameWithBoardSizeMoreThan12() {
        GameCreateDto gameCreateDto = new GameCreateDto(13);
        Exception ex = assertThrows(InvalidBoardSizeException.class, () ->
                ticTacToeService.createANewGame(gameCreateDto));
        String expectedMessage = "Invalid board size was given";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void determineWinnerOfTheGameOfUnknownID() {
        String[] moves = new String[]{"CIRCLE 0 0", "CROSS 1 1",
                "CIRCLE 0 2",
                "CROSS 2 1",
                "CIRCLE 0 1",
                "CROSS 1 0",
                "CIRCLE 1 2",
                "CROSS 2 0",
                "CIRCLE 2 2"};
        InsertMovesDto insertMovesDto = new InsertMovesDto("6235ecb24f23c02b722defc5", moves);

        TicTacToe ticTacToe = new TicTacToe(
                "6235ecb24f23c02b722defc4", GameStatus.NEW, 3, null, null
        );

        given(ticTocRepository.findById("6235ecb24f23c02b722defc4")).willReturn(Optional.of(ticTacToe));

        Exception ex = assertThrows(NotFoundException.class, () ->
                ticTacToeService.determineWinnerOfTheGame(insertMovesDto));
        String expectedMessage = "Game of game id: " + insertMovesDto.getGame_ID() + "not found";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void determineWinnerOfTheGameOfInProgress() {
        String[] moves = new String[]{"CIRCLE 0 0", "CROSS 1 1",
                "CIRCLE 0 2"};
        InsertMovesDto insertMovesDto = new InsertMovesDto("6235ecb24f23c02b722defc4", moves);

        TicTacToe ticTacToe = new TicTacToe(
                "6235ecb24f23c02b722defc4", GameStatus.NEW, 3, null, null
        );

        given(ticTocRepository.findById("6235ecb24f23c02b722defc4")).willReturn(Optional.of(ticTacToe));

        TicTacToeDto ticTacToeDto = ticTacToeService.determineWinnerOfTheGame(insertMovesDto);

        assertNotNull(ticTacToeDto);
        assertEquals(GameStatus.IN_PROGRESS, ticTacToeDto.getGameStatus());
    }


    @Test
    void determineWinnerOfTheGameOfInProgressWithoutNullMoves() {
        String[] moves = new String[]{"CIRCLE 0 0", "CROSS 1 1",
                "CIRCLE 0 2"};
        InsertMovesDto insertMovesDto = new InsertMovesDto("6235ecb24f23c02b722defc4", moves);

        String[] existingMoves = new String[]{"CIRCLE 0 0"};

        TicTacToe ticTacToe = new TicTacToe(
                "6235ecb24f23c02b722defc4", GameStatus.NEW, 3, null, existingMoves
        );

        given(ticTocRepository.findById("6235ecb24f23c02b722defc4")).willReturn(Optional.of(ticTacToe));

        TicTacToeDto ticTacToeDto = ticTacToeService.determineWinnerOfTheGame(insertMovesDto);

        assertNotNull(ticTacToeDto);
        assertEquals(GameStatus.IN_PROGRESS, ticTacToeDto.getGameStatus());
    }

    @Test
    void determineWinnerIsCircle() {
        String[] moves = new String[]{
                "CIRCLE 0 0",
                "CROSS 1 1",
                "CIRCLE 0 2",
                "CROSS 2 1",
                "CIRCLE 0 1",
                "CROSS 1 0",
                "CIRCLE 1 2",
                "CROSS 2 0",
                "CIRCLE 2 2"
        };
        InsertMovesDto insertMovesDto = new InsertMovesDto("6235ecb24f23c02b722defc4", moves);

//        String[] existingMoves = new String[]{"CIRCLE 0 0"};

        TicTacToe ticTacToe = new TicTacToe(
                "6235ecb24f23c02b722defc4", GameStatus.NEW, 3, null, null
        );

        List<Coordinates> coordinates = List.of(
                new Coordinates(0, 0),
                new Coordinates(0, 2),
                new Coordinates(0, 1),
                new Coordinates(1, 2),
                new Coordinates(2, 2));

        given(ticTocRepository.findById("6235ecb24f23c02b722defc4")).willReturn(Optional.of(ticTacToe));
        given(gameWinnerService.checkWinningStatus(coordinates,ticTacToe.getSize())).willReturn(true);

        TicTacToeDto ticTacToeDto = ticTacToeService.determineWinnerOfTheGame(insertMovesDto);

        assertNotNull(ticTacToeDto);
        assertEquals(GameStatus.FINISHED, ticTacToeDto.getGameStatus());
        assertEquals("CIRCLE", ticTacToeDto.getWinner());
    }
}