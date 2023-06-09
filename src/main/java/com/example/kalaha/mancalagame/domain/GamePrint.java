package com.example.kalaha.mancalagame.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface GamePrint {

  String TEMPLATE = """
                   Player Two
         | %02d | %02d | %02d | %02d | %02d | %02d |
    (%02d)                                 (%02d)
         | %02d | %02d | %02d | %02d | %02d | %02d |
                   Player One
    """;

  static String board(Board board) {
    Player player1 = board.getPlayers().player1();
    Player player2 = board.getPlayers().player2();
    List<House> p2Rev = new ArrayList<>(player2.houses());

    Collections.reverse(p2Rev);
    List<Pit> pits = new ArrayList<>(p2Rev);
    pits.add(player2.store());
    pits.add(player1.store());
    pits.addAll(player1.houses());

    return String.format(TEMPLATE, pits.stream()
      .map(Pit::count)
      .toList()
      .toArray());
  }

}
