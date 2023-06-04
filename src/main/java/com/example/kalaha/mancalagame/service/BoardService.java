package com.example.kalaha.mancalagame.service;

import com.example.kalaha.mancalagame.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.example.kalaha.mancalagame.domain.PlayerNumber.ONE;
import static com.example.kalaha.mancalagame.domain.PlayerNumber.TWO;

@Service
public class BoardService {
  public Board createBoard() {
    Board board = new Board(6, 6);
    var stones = Stream.generate(() -> board.numberOfStones).limit(board.numberOfPits).toList();
    return from(stones, 0, stones, 0);
  }

  public Board from(List<Integer> p1Houses, int p1Store, List<Integer> p2Houses, int p2Store) {
    LinkedList<House> listOfHouseOnSideOne = createHouses(ONE, p1Houses);
    LinkedList<House> listOfHouseOnSideTwo = createHouses(TWO, p2Houses);
    setOppositeHouse(listOfHouseOnSideOne, listOfHouseOnSideTwo);

    Store storeHouseOne = new Store(ONE, p1Store);
    Store storeHouseTwo = new Store(TWO, p2Store);

    setCircularMove(listOfHouseOnSideOne, storeHouseOne, listOfHouseOnSideTwo, storeHouseTwo);

    Player player1 = new Player(ONE, listOfHouseOnSideOne, storeHouseOne);
    Player player2 = new Player(TWO, listOfHouseOnSideTwo, storeHouseTwo);

    Board board = new Board();
    board.listOfHouses = new ArrayList<>(listOfHouseOnSideOne);
    board.listOfHouses.addAll(listOfHouseOnSideTwo);
    board.listOfStores = List.of(storeHouseOne, storeHouseTwo);
    board.players = new Players(player1, player2);

    return board;
  }

  private void setCircularMove(LinkedList<House> listOfHouseOnSideOne, Store storeHouseOne, LinkedList<House> listOfHouseOnSideTwo, Store storeHouseTwo) {
    listOfHouseOnSideOne.getLast().setNext(storeHouseOne);
    storeHouseOne.setNext(listOfHouseOnSideTwo.getFirst());
    listOfHouseOnSideTwo.getLast().setNext(storeHouseTwo);
    storeHouseTwo.setNext(listOfHouseOnSideOne.getFirst());
  }

  private void setOppositeHouse(List<House> listOfHouseOnSideOne, List<House> listOfHouseOnSideTwo) {
    for (int i = 0; i < listOfHouseOnSideOne.size(); i++) {
      House one = listOfHouseOnSideOne.get(i);
      House two = listOfHouseOnSideTwo.get(listOfHouseOnSideTwo.size() - i - 1);

      one.setOpposite(two);
      two.setOpposite(one);
    }
  }

  public LinkedList<House> createHouses(PlayerNumber playerNumber, List<Integer> stones) {
    LinkedList<House> houses = new LinkedList<>();
    houses.addLast(new House(playerNumber, stones.get(0)));
    while (houses.size() < stones.size()) {
      House house = new House(playerNumber, stones.get(houses.size()));
      houses.getLast().setNext(house);
      houses.addLast(house);
    }
    return houses;
  }
}
