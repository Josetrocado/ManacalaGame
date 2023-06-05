package com.example.kalaha.mancalagame.service;

import com.example.kalaha.mancalagame.domain.*;
import org.springframework.stereotype.Service;


@Service
public class GameServiceImpl implements GameService {
  private Board board;

  private Player player;

  private Status status;


  public static GameServiceImpl create(Board board) {
    GameServiceImpl game = new GameServiceImpl();
    game.board = board;
    game.player = board.getPlayers().player1();
    game.status = Status.ACTIVE;
    return game;
  }

  public Player getActivePlayer() {
    return player;
  }

  public Result move(PlayerNumber playerNumber, int house) throws IllegalAccessException {
    if (!player.playerNumber().equals(playerNumber)) {
      throw new IllegalStateException(String.format("Player %s cannot take their turn yet", playerNumber));
    }

    Pit landed = player.turn(house);
    if (player.complete()) {
      swap().finish();
      status = declareWinner();
    }
    player = nextPlayer(landed);
    return new Result(status, player.playerNumber(), board);
  }

  public Status declareWinner() {
    Players players = board.getPlayers();
    int score1 = players.player1().score();
    int score2 = players.player2().score();

    if (score1 > score2) {
      return Status.PLAYER_ONE_WIN;
    } else if (score2 > score1) {
      return Status.PLAYER_TWO_WIN;
    }
    return Status.DRAW;
  }

  public Player swap() {
    Players players = board.getPlayers();
    return switch (player.playerNumber()) {
      case ONE -> players.player2();
      case TWO -> players.player1();
    };
  }

  public Player nextPlayer(Pit landed) {
    if (landed.equals(player.store())) {
      return player;
    }
    return swap();
  }

  public ApiResponse generateBoardString(Result result) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setBoard(GamePrint.board(result.board()));;
    apiResponse.setNextPlayer(result.next().toString());
    apiResponse.setStatus(result.status().toString());
    return apiResponse;
  }

  public PlayerNumber determinePlayer(String playerNumber) {
    if (playerNumber.equals("ONE")) {
      return PlayerNumber.ONE;
    }
    return PlayerNumber.TWO;
  }

  public ApiResponse getStartingBoard() {

    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setBoard("""
                     Player Two
           | 06 | 06 | 06 | 06 | 06 | 06 |
      (00)                                 (00)
           | 06 | 06 | 06 | 06 | 06 | 06 |
                     Player One
      """);

    return apiResponse;
  }
}
