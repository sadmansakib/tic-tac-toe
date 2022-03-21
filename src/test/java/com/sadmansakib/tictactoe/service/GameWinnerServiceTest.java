package com.sadmansakib.tictactoe.service;

import com.sadmansakib.tictactoe.entity.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameWinnerServiceTest {

    private GameWinnerService gameWinnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameWinnerService = new GameWinnerService();
    }

    @Test
    void checkWinningStatusForCircle() {
        List<Coordinates> coordinates = List.of(
                new Coordinates(0, 0),
                new Coordinates(0, 2),
                new Coordinates(0, 1),
                new Coordinates(1, 2),
                new Coordinates(2, 2));

        boolean result = gameWinnerService.checkWinningStatus(coordinates, 3);
        assertTrue(result);
    }

    @Test
    void checkWinningStatusForCircleDiagonally() {
        List<Coordinates> coordinates = List.of(
                new Coordinates(0, 0),
                new Coordinates(1, 1),
                new Coordinates(2, 2));

        boolean result = gameWinnerService.checkWinningStatus(coordinates, 3);
        assertTrue(result);
    }

    @Test
    void checkWinningStatusForCircleOppositeDiagonally() {
        List<Coordinates> coordinates = List.of(
                new Coordinates(0, 2),
                new Coordinates(1, 1),
                new Coordinates(2, 0));

        boolean result = gameWinnerService.checkWinningStatus(coordinates, 3);
        assertTrue(result);
    }

    @Test
    void checkWinningStatusForCross() {
        List<Coordinates> coordinates = List.of(
                new Coordinates(0, 3),
                new Coordinates(1, 3),
                new Coordinates(2, 3),
                new Coordinates(3, 3));

        boolean result = gameWinnerService.checkWinningStatus(coordinates, 4);
        assertTrue(result);
    }
}