package com.example.kalaha.mancalagame.service;

import com.example.kalaha.mancalagame.domain.Player;
import com.example.kalaha.mancalagame.domain.PlayerNumber;
import com.example.kalaha.mancalagame.domain.Result;
import com.example.kalaha.mancalagame.domain.Status;

public interface GameService {

  Player getActivePlayer();

  Result move(PlayerNumber playerNumber, int house) throws IllegalAccessException;

  Status declareWinner();

  Player swap();

  String generateBoardString(Result result);

  PlayerNumber determinePlayer(String playerNumber);

  String getStartingBoard();
}
