package com.sadmansakib.tictactoe.service;

import com.sadmansakib.tictactoe.entity.Coordinates;
import com.sadmansakib.tictactoe.entity.dao.TicTacToe;
import com.sadmansakib.tictactoe.entity.dto.GameCreateDto;
import com.sadmansakib.tictactoe.entity.dto.InsertMovesDto;
import com.sadmansakib.tictactoe.entity.dto.TicTacToeDto;
import com.sadmansakib.tictactoe.entity.enums.GameStatus;
import com.sadmansakib.tictactoe.entity.mappings.GameMapper;
import com.sadmansakib.tictactoe.exception.InvalidBoardSizeException;
import com.sadmansakib.tictactoe.exception.NotFoundException;
import com.sadmansakib.tictactoe.repository.TicTocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TicTacToeService {
    private final TicTocRepository ticTocRepository;
    private final MongoTemplate mongoTemplate;
    private final GameWinnerService gameWinnerService;


    Logger logger = LoggerFactory.getLogger(TicTacToeService.class);

    @Autowired
    public TicTacToeService(
            TicTocRepository ticTocRepository,
            GameWinnerService gameWinnerService,
            MongoTemplate mongoTemplate) {
        this.ticTocRepository = ticTocRepository;
        this.mongoTemplate = mongoTemplate;
        this.gameWinnerService = gameWinnerService;
    }

    public TicTacToeDto createANewGame(GameCreateDto gameCreateDto) {
        if (gameCreateDto.getSize() < 3 || gameCreateDto.getSize() > 12) {
            throw new InvalidBoardSizeException("Invalid board size was given");
        } else {
            TicTacToe newGame = new TicTacToe(GameStatus.NEW, gameCreateDto.getSize() == 0 ? 3 : gameCreateDto.getSize());
            ticTocRepository.insert(newGame);

            return GameMapper.INSTANCE.ticTacToeToDto(newGame);
        }
    }

    public TicTacToeDto determineWinnerOfTheGame(InsertMovesDto insertMovesDto) {

        TicTacToe currentGame = ticTocRepository
                .findById(insertMovesDto.getGame_ID())
                .orElseThrow(() -> new NotFoundException("Game of game id: " + insertMovesDto.getGame_ID() + "not found"));

        int boardSize = currentGame.getSize();

        String[] existingMoves = currentGame.getMoves();

        int numberOfMoves;

        if (existingMoves == null) {
            numberOfMoves = getNumberOfMovesWhenExistingMovesIsNull(insertMovesDto);
            existingMoves = insertMovesDto.getMoves();
        } else {
            numberOfMoves = getNumberOfMovesWhenExistingMovesIsNotNull(insertMovesDto, existingMoves);
        }

        if (numberOfMoves >= boardSize + boardSize - 1 && numberOfMoves <= boardSize * boardSize) {
            List<Coordinates> movesOfCircle = new ArrayList<>();
            List<Coordinates> movesOfCross = new ArrayList<>();
            for (String move : existingMoves) {
                String[] entry = move.split(" ");
                Coordinates coordinates = new Coordinates(Integer.parseInt(entry[1]), Integer.parseInt(entry[2]));
                if (entry[0].equals("CIRCLE")) {
                    movesOfCircle.add(coordinates);
                } else {
                    movesOfCross.add(coordinates);
                }
            }

            logger.debug("movement of circle: " + movesOfCircle);

            boolean isCircleWinner = gameWinnerService.checkWinningStatus(movesOfCircle, boardSize);
            boolean isCrossWinner = gameWinnerService.checkWinningStatus(movesOfCross, boardSize);

            logger.info("did circle won: " + isCircleWinner);
            logger.info("did cross won: " + isCrossWinner);

            if (isCircleWinner) {
                setWinner(insertMovesDto, "CIRCLE");
                return new TicTacToeDto(
                        currentGame.getID(),
                        currentGame.getSize(),
                        GameStatus.FINISHED,
                        "CIRCLE");
            } else if (isCrossWinner) {
                setWinner(insertMovesDto, "CROSS");
                return new TicTacToeDto(
                        currentGame.getID(),
                        currentGame.getSize(),
                        GameStatus.FINISHED,
                        "CROSS");
            } else {
                setWinner(insertMovesDto, null);
                return new TicTacToeDto(
                        currentGame.getID(),
                        currentGame.getSize(),
                        GameStatus.FINISHED,
                        null);
            }
        } else {
            return new TicTacToeDto(currentGame.getID(), currentGame.getSize(), GameStatus.IN_PROGRESS, null);
        }
    }

    private void setWinner(InsertMovesDto insertMovesDto, String winner) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(insertMovesDto.getGame_ID()));

        Update updateQuery = new Update();
        updateQuery.set("winner", winner);
        updateQuery.set("gamestatus", GameStatus.FINISHED);

        mongoTemplate.findAndModify(query, updateQuery, TicTacToe.class);
    }

    private int getNumberOfMovesWhenExistingMovesIsNotNull(InsertMovesDto insertMovesDto, String[] existingMoves) {
        int numberOfMoves;
        var updatedMoves = Stream.concat(
                        Arrays.stream(existingMoves),
                        Arrays.stream(insertMovesDto.getMoves()))
                .toArray(size -> (String[]) Array.newInstance(existingMoves.getClass().getComponentType(), size));
        numberOfMoves = updatedMoves.length;
        return numberOfMoves;
    }

    private int getNumberOfMovesWhenExistingMovesIsNull(InsertMovesDto insertMovesDto) {
        int numberOfMoves;
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(insertMovesDto.getGame_ID()));

        Update updateQuery = new Update();
        updateQuery.set("moves", insertMovesDto.getMoves());

        mongoTemplate.findAndModify(query, updateQuery, TicTacToe.class);

        numberOfMoves = insertMovesDto.getMoves().length;
        return numberOfMoves;
    }
}
