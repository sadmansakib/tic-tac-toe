package com.sadmansakib.tictactoe.repository;

import com.sadmansakib.tictactoe.entity.dao.TicTacToe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicTocRepository extends MongoRepository<TicTacToe, String> {
}
