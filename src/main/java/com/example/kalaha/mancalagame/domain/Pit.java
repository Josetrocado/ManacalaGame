package com.example.kalaha.mancalagame.domain;

import java.util.Optional;

public abstract class Pit {
  public int stones;

  public PlayerNumber playerNumber;
  private Pit next;

  protected Pit(PlayerNumber playerNumber, int stones) {
    this.playerNumber = playerNumber;
    this.stones = stones;
  }

  abstract boolean canAddStones(PlayerNumber playerNumber);

  public Optional<House> getOpposite() {
    return Optional.empty();
  }

  public Integer captureStones() {
    if (this.getOpposite().isEmpty()) {
      return 0;
    }
    return this.getOpposite().get().takeStones();
  }

  public int takeStones() {
    return 0;
  }

  public int count() {
    return stones;
  }

  public void addStone() {
    this.stones++;
  }

  public boolean isEmpty() {
    return this.stones == 0;
  }

  public Pit next() {
    return next;
  }

  public Pit setNext(Pit next) {
    this.next = next;
    return next;
  }

}
