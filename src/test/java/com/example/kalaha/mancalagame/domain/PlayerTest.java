package com.example.kalaha.mancalagame.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.kalaha.mancalagame.domain.PlayerNumber.ONE;
import static com.example.kalaha.mancalagame.domain.PlayerNumber.TWO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

  @Test
  public void playerNeedsToMoveStones() throws IllegalAccessException {
    House last = new House(ONE, 0);
    House middle = new House(ONE, 0);
    House first = new House(ONE, 2);
    Store end = new Store(ONE, 0);

    first.setNext(middle).setNext(last).setNext(end);

    Player player = new Player(ONE, List.of(first, middle, last), end);
    Pit landed = player.turn(1);

    assertThat(landed, is(last));
    assertThat(first.count(), is(0));
    assertThat(middle.count(), is(1));
    assertThat(last.count(), is(1));
  }

  @Test
  void playerCannotSelectEmptyHouse() {
    assertThrows(IllegalArgumentException.class, () -> {
      Player player = new Player(ONE, List.of(new House(ONE, 0)), new Store(ONE, 0));
      player.turn(1);
    });
  }


  @Test
  void playerCannotChooseHouseBelowRange() {
    assertThrows(IllegalArgumentException.class, () -> {
      Player player = new Player(ONE, List.of(new House(ONE, 0)), new Store(ONE, 0));
      player.turn(0);
    });
  }

  @Test
  void playerCannotChooseHouseAboveRange() {
    assertThrows(IllegalArgumentException.class, () -> {
      Player player = new Player(ONE, List.of(new House(ONE, 0)), new Store(ONE, 0));
      player.turn(2);
    });
  }


  @Test
  public void playerSkipsOpponentsStore() throws IllegalAccessException {
    House myHouse = new House(ONE, 3);
    Store myStore = new Store(ONE, 0);

    House oppnentHouse = new House(TWO, 0);
    Store oppnentStore = new Store(TWO, 0);

    myHouse.setNext(myStore).setNext(oppnentHouse).setNext(oppnentStore).setNext(myHouse);

    Player player = new Player(ONE, List.of(myHouse), myStore);
    Pit landed = player.turn(1);

    assertThat(landed, is(myHouse));
    assertThat(myHouse.count(), is(1));
    assertThat(myStore.count(), is(1));
    assertThat(oppnentHouse.count(), is(1));
    assertThat(oppnentStore.count(), is(0));

  }
}
