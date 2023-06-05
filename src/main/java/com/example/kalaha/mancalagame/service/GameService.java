package com.example.kalaha.mancalagame.service;

import com.example.kalaha.mancalagame.domain.*;

public interface GameService {

  Player getActivePlayer();

  Result move(PlayerNumber playerNumber, int house) throws IllegalAccessException;

  Status declareWinner();

  Player swap();

  ApiResponse generateBoardString(Result result);

  PlayerNumber determinePlayer(String playerNumber);

  ApiResponse getStartingBoard();
}
