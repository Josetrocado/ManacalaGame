package com.example.kalaha.mancalagame.domain;


public class Store extends Pit {

  public Store(PlayerNumber playerNumber, int stones) {
    super(playerNumber, stones);
  }

  @Override
  boolean canAddStones(PlayerNumber player) {
    return player.equals(playerNumber);
  }

  public void addStones(int stones) {
    this.stones += stones;
  }

  public PlayerNumber getOwner() {
    return this.playerNumber;
  }

}
