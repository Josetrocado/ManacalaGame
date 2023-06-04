package com.example.kalaha.mancalagame.domain;

import java.util.List;

public record Player(PlayerNumber playerNumber, List<House> houses, Store store) {

  private void checkHouseForStones(House house) {
    if (house.isEmpty()) {
      throw new IllegalArgumentException("House must have stones to take a turn");
    }
  }

  public Pit turn(int houseNumber) throws IllegalAccessException {
    House house = getHouse(houseNumber);
    checkHouseForStones(house);
    Pit pit = takeTurn(house);
    if (shouldCaptureOppositeStone(pit)) {
      store.addStones(pit.takeStones());
      store.addStones(pit.captureStones());
    }
    return pit;
  }

  private boolean shouldCaptureOppositeStone(Pit pit) {
    return pit.count() == 1 && pit.getOpposite().map(opposite -> opposite.getOwner() != playerNumber).orElse(false);
  }

  public boolean complete() {
    return houses.stream().allMatch(House::isEmpty);
  }

  public void finish() {
    for (House house : houses) {
      store.addStones(house.takeStones());
    }
  }

  public int score() {
    return store.count();
  }

  private Pit takeTurn(House house) {
    Integer stones = house.takeStones();
    Pit pit = house;

    while (stones > 0) {
      pit = pit.next();
      if (pit.canAddStones(playerNumber)) {
        stones--;
        pit.addStone();
      }
    }
    return pit;
  }

  private House getHouse(int houseNumber) throws IllegalAccessException {
    if (houseNumber < 1 || houseNumber > houses.size()) {
      throw new IllegalArgumentException("House number must be between 1 and " + houses.size());
    }
    return this.houses.get(houseNumber - 1);
  }

}
