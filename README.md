# TIC-TAC-TOE

A REST API application for a game of tic-tac-toe built with Spring Boot and MongoDB as its database

Base URL: `https://tic-tac-toe-api-sadman.herokuapp.com`
Base URL for API: `https://tic-tac-toe-api-sadman.herokuapp.com/api/v1/tictactoe`

## Project Structure

```bash
src
├───main
│   ├───java
│   │   └───com
│   │       └───sadmansakib
│   │           └───tictactoe
│   │               │   TicTacToeApplication.java
│   │               │
│   │               ├───controller
│   │               │       TicTacToeController.java
│   │               │
│   │               ├───entity
│   │               │   │   Coordinates.java
│   │               │   │
│   │               │   ├───dao
│   │               │   │       TicTacToe.java
│   │               │   │
│   │               │   ├───dto
│   │               │   │       GameCreateDto.java
│   │               │   │       InsertMovesDto.java
│   │               │   │       TicTacToeDto.java
│   │               │   │
│   │               │   ├───enums
│   │               │   │       GameStatus.java
│   │               │   │
│   │               │   └───mappings
│   │               │           GameMapper.java
│   │               │
│   │               ├───exception
│   │               │       ApiExceptionHandler.java
│   │               │       ApiExceptionPayload.java
│   │               │       InvalidBoardSizeException.java
│   │               │       NotFoundException.java
│   │               │
│   │               ├───repository
│   │               │       TicTocRepository.java
│   │               │
│   │               └───service
│   │                       GameWinnerService.java
│   │                       TicTacToeService.java
│   │
│   └───resources
│       │   application.properties
│       │
│       ├───static
│       └───templates
└───test
    └───java
        └───com
            └───sadmansakib
                └───tictactoe
                    │   TicTacToeApplicationTests.java
                    │
                    └───service
                            GameWinnerServiceTest.java
                            TicTacToeServiceTest.java
```

## Endpoints

- ### Request
    `POST /create`
    ```bash
    curl --location --request POST 'https://tic-tac-toe-api-sadman.herokuapp.com/api/v1/tictactoe/create' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "size": 3
    }'
    ```
  
- ### Response
    ```json 
  {
    "game_ID": "623c47b2c8b8e02e692e5dee",
    "boardSize": 3,
    "gameStatus": "NEW",
    "winner": null
  }
  ```

- ### Request
  `POST /get-winner`
    ```bash
    curl --location --request POST 'https://tic-tac-toe-api-sadman.herokuapp.com/api/v1/tictactoe/get-winner' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "game_ID": "623c47b2c8b8e02e692e5dee",
        "moves": [
            "CIRCLE 0 0",
            "CROSS 1 1",
            "CIRCLE 0 2",
            "CROSS 2 1",
            "CIRCLE 0 1",
            "CROSS 1 0",
            "CIRCLE 1 2",
            "CROSS 2 0",
            "CIRCLE 2 2"
       ]
    }'
    ```

- ### Response
    ```json 
  {
    "game_ID": "623c47b2c8b8e02e692e5dee",
    "boardSize": 3,
    "gameStatus": "FINISHED",
    "winner": "CIRCLE"
  }
  ```

- ### Request
  `GET /actuator/health`
    ```bash
    curl --location --request GET 'https://tic-tac-toe-api-sadman.herokuapp.com/actuator/health'
    ```

- ### Response
    ```json 
  {
    "status": "UP"
  }
  ```