package com.example.kalaha.mancalagame.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class HouseTest {
  PlayerNumber playerNumber = PlayerNumber.ONE;

  @Test
  public void testStonesInHouse() {
    House house = new House(playerNumber, 6);
    assertThat(house.count(), is(6));
  }

  @Test
  public void removeStonesFromHouse() {
    House house = new House(playerNumber, 6);
    assertThat(house.count(), is(6));

    int removedStones = house.takeStones();

    assertThat(removedStones, is(6));
    assertThat(house.count(), is(0));
  }

  @Test
  public void addStonesToAHouse() {

    House house = new House(playerNumber, 0);
    house.addStone();
    assertThat(house.count(), is(1));
  }

  @Test
  public void addStoneToStore() {
    Store store = new Store(PlayerNumber.TWO, 0);
    store.addStone();
    assertThat(store.count(), is(1));
  }

  @Test
  public void addMultipleStonesToStore() {
    Store store = new Store(PlayerNumber.TWO, 0);
    store.addStones(6);
    assertThat(store.count(), is(6));
  }

}
