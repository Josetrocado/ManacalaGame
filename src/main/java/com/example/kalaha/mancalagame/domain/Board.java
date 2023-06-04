package com.example.kalaha.mancalagame.domain;

import java.util.List;


public class Board {

  public int numberOfStones;

  public int numberOfPits;
  public List<House> listOfHouses;
  public List<Store> listOfStores;

  public Players players;

  public Board(int numberOfStones, int numberOfPits) {
    this.numberOfStones = numberOfStones;
    this.numberOfPits = numberOfPits;
  }

  public Board() {
  }

  public List<House> getHouses() {
    return listOfHouses;
  }

  public List<Store> getStores() {
    return listOfStores;
  }

  public Players getPlayers() {
    return players;
  }
}
