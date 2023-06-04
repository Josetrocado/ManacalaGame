package com.example.kalaha.mancalagame.domain;

import java.util.Optional;

public class House extends Pit {

  private House opposite;

  public House(PlayerNumber playerNumber, int stones) {
    super(playerNumber, stones);
  }

  @Override
  boolean canAddStones(PlayerNumber player) {
    return true;
  }

  @Override
  public Optional<House> getOpposite() {
    return Optional.ofNullable(opposite);
  }

  public void setOpposite(House opposite) {
    this.opposite = opposite;
  }

  @Override
  public int takeStones() {
    int stones = this.stones;
    this.stones = 0;
    return stones;
  }

  public PlayerNumber getOwner() {
    return this.playerNumber;
  }


}
