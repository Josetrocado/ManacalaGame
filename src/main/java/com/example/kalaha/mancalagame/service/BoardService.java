package com.example.kalaha.mancalagame.service;

import com.example.kalaha.mancalagame.domain.Board;
import com.example.kalaha.mancalagame.domain.House;
import com.example.kalaha.mancalagame.domain.PlayerNumber;
import com.example.kalaha.mancalagame.domain.Store;

import java.util.LinkedList;
import java.util.List;

public interface BoardService {

  Board createBoard();

  Board from(List<Integer> p1Houses, int p1Store, List<Integer> p2Houses, int p2Store);

  void setCircularMove(LinkedList<House> listOfHouseOnSideOne, Store storeHouseOne, LinkedList<House> listOfHouseOnSideTwo, Store storeHouseTwo);

  void setOppositeHouse(List<House> listOfHouseOnSideOne, List<House> listOfHouseOnSideTwo);

  LinkedList<House> createHouses(PlayerNumber playerNumber, List<Integer> stones);

}
