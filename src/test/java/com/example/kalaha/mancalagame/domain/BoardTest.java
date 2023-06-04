package com.example.kalaha.mancalagame.domain;

import com.example.kalaha.mancalagame.service.BoardService;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class BoardTest {

  private final BoardService boardService = new BoardService();

  @Test
  public void testBoardHouses() {
    Board board = boardService.createBoard();

    List<House> houses = board.getHouses();
    Map<PlayerNumber, List<House>> sides = houses.stream().collect(Collectors.groupingBy(House::getOwner));
    assertThat(sides.get(PlayerNumber.ONE).size(), is(6));
    assertThat(sides.get(PlayerNumber.TWO).size(), is(6));
  }

  @Test
  public void testStoreHouseOnBoard() {
    Board board = boardService.createBoard();
    List<Store> stores = board.getStores();
    Map<PlayerNumber, List<Store>> sidesOfBoard = stores.stream().collect(Collectors.groupingBy(Store::getOwner));
    assertThat(sidesOfBoard.get(PlayerNumber.ONE).size(), is(1));
    assertThat(sidesOfBoard.get(PlayerNumber.TWO).size(), is(1));
  }

  @Test
  public void testBoardHasTwoPlayers() {
    Board board = boardService.createBoard();
    Players players = board.getPlayers();
    assertThat(players.player1().playerNumber(), is(PlayerNumber.ONE));
    assertThat(players.player2().playerNumber(), is(PlayerNumber.TWO));
  }

  @Test
  public void houseNeedToHaveOppositeHouses() {
    Board board = boardService.createBoard();

    Players players = board.getPlayers();
    List<House> playerOneHouses = players.player1().houses();
    List<House> playerTwoHouses = players.player2().houses();

    assertThat(playerOneHouses.get(0).getOpposite().get(), is(playerTwoHouses.get(5)));
    assertThat(playerOneHouses.get(1).getOpposite().get(), is(playerTwoHouses.get(4)));
    assertThat(playerOneHouses.get(2).getOpposite().get(), is(playerTwoHouses.get(3)));
    assertThat(playerOneHouses.get(3).getOpposite().get(), is(playerTwoHouses.get(2)));
    assertThat(playerOneHouses.get(4).getOpposite().get(), is(playerTwoHouses.get(1)));
    assertThat(playerOneHouses.get(5).getOpposite().get(), is(playerTwoHouses.get(0)));
    assertThat(playerTwoHouses.get(0).getOpposite().get(), is(playerOneHouses.get(5)));
    assertThat(playerTwoHouses.get(1).getOpposite().get(), is(playerOneHouses.get(4)));
    assertThat(playerTwoHouses.get(2).getOpposite().get(), is(playerOneHouses.get(3)));
    assertThat(playerTwoHouses.get(3).getOpposite().get(), is(playerOneHouses.get(2)));
    assertThat(playerTwoHouses.get(4).getOpposite().get(), is(playerOneHouses.get(1)));
    assertThat(playerTwoHouses.get(5).getOpposite().get(), is(playerOneHouses.get(0)));
  }

  @Test
  public void pitNeedToCycle() {
    Board board = boardService.createBoard();
    Pit first = board.getHouses().get(0);
    Pit pit = first;

    Set<Pit> all = new HashSet<>();
    all.add(pit);

    for (int i = 0; i < 14; i++) {
      pit = pit.next();
      all.add(pit);
    }

    assertThat(pit, is(first));
    assertThat(all.size(), is(14));
  }

}
