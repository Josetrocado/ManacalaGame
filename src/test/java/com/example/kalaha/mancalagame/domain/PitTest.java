package com.example.kalaha.mancalagame.domain;

import org.junit.jupiter.api.Test;

import static com.example.kalaha.mancalagame.domain.PlayerNumber.ONE;
import static com.example.kalaha.mancalagame.domain.PlayerNumber.TWO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PitTest {

  @Test
  public void shouldHaveStonesInHouse() {
    Pit house = new House(TWO, 6);
    assertThat(house.count(), is(6));
  }

  @Test
  public void personalStoresShouldBeEmpty() {
    Pit personalStore = new Store(ONE, 0);
    assertThat(personalStore.count(), is(0));
  }

  @Test
  public void pointToTheNextPit() {
    Pit pit = new House(TWO, 6);
    Store store = new Store(TWO, 0);
    House house = new House(TWO, 6);

    pit.setNext(store);
    store.setNext(house);

    assertThat(pit.next(), is(store));
    assertThat(store.next(), is(house));
  }

}
