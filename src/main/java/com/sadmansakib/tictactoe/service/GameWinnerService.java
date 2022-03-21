package com.sadmansakib.tictactoe.service;

import com.sadmansakib.tictactoe.entity.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameWinnerService {

    Logger logger = LoggerFactory.getLogger(GameWinnerService.class);

    public boolean checkWinningStatus(List<Coordinates> movementList, int boardSize) {
        int[] rowContainer = new int[boardSize];
        int[] columnContainer = new int[boardSize];
        int[] diagonalContainer = new int[boardSize];
        int[] oppositeDiagonalContainer = new int[boardSize];

        logger.debug("movement list: "+movementList);
        logger.debug("boardSize: "+boardSize);

        for (Coordinates movement :
                movementList) {
            int row = movement.getX();
            int column = movement.getY();

            rowContainer[row] += 1;
            columnContainer[column] += 1;

            if (row == column){
                diagonalContainer[row] += 1;
            }

            if (row + column + 1 == boardSize){
                oppositeDiagonalContainer[row] += 1;
            }

            // Now check for win across either direction
            if (rowContainer[row] == boardSize || columnContainer[column] == boardSize ) {
                return true;
            }

            int sumForRegularDiagonalElements = 0;
            int sumForOppositeDiagonalElements = 0;

            for (int i = 0; i < diagonalContainer.length; i++) {
                sumForRegularDiagonalElements += diagonalContainer[i];
                sumForOppositeDiagonalElements += oppositeDiagonalContainer[i];
            }

            if (sumForRegularDiagonalElements == boardSize || sumForOppositeDiagonalElements == boardSize){
                return true;
            }
        }

        return false;
    }
}
