package com.example.kalaha.mancalagame.service;

import com.example.kalaha.mancalagame.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.kalaha.mancalagame.domain.PlayerNumber.ONE;
import static com.example.kalaha.mancalagame.domain.PlayerNumber.TWO;
import static com.example.kalaha.mancalagame.domain.Status.ACTIVE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceTest {

  private final BoardService boardServiceImpl = new BoardServiceImpl();

  @Test
  public void testActivePlayer() {
    GameService game = GameServiceImpl.create(boardServiceImpl.createBoard());
    Player player = game.getActivePlayer();
    assertThat(player.playerNumber(), is(ONE));
  }

  @Test
  void rejectMoveByInActive() {
    GameService game = GameServiceImpl.create(boardServiceImpl.createBoard());
    assertThrows(IllegalStateException.class, () -> game.move(TWO, 1));
  }

  @Test
  void shouldAllowPlayerToAddStones() throws IllegalAccessException {
    GameService game = GameServiceImpl.create(boardServiceImpl.createBoard());
    Result result = game.move(ONE, 1);
    var actual = GamePrint.board(result.board());
    assertThat(actual, is("""
                     Player Two
           | 06 | 06 | 06 | 06 | 06 | 06 |
      (00)                                 (01)
           | 00 | 07 | 07 | 07 | 07 | 07 |
                     Player One
      """));
    assertThat(result.status(), is(ACTIVE));
  }

  @Test
  void shouldAllowAlternateTurns() throws IllegalAccessException {
    GameService game = GameServiceImpl.create(boardServiceImpl.createBoard());

    // player one makes a move on pit 1
    Result result = game.move(ONE, 1);
    var actual = GamePrint.board(result.board());
    assertThat(actual, is("""
                     Player Two
           | 06 | 06 | 06 | 06 | 06 | 06 |
      (00)                                 (01)
           | 00 | 07 | 07 | 07 | 07 | 07 |
                     Player One
      """));
    assertThat(result.next(), is(ONE));

    // player one takes their turn on pit 2
    result = game.move(ONE, 2);
    actual = GamePrint.board(result.board());
    assertThat(actual, is("""
                     Player Two
           | 06 | 06 | 06 | 06 | 07 | 07 |
      (00)                                 (02)
           | 00 | 00 | 08 | 08 | 08 | 08 |
                     Player One
      """));
    assertThat(result.next(), is(TWO));
    assertThat(result.status(), is(ACTIVE));

    result = game.move(TWO, 1);
    actual = GamePrint.board(result.board());
    assertThat(actual, is("""
                     Player Two
           | 07 | 07 | 07 | 07 | 08 | 00 |
      (01)                                 (02)
           | 01 | 00 | 08 | 08 | 08 | 08 |
                     Player One
      """));
    assertThat(result.next(), is(ONE));
    assertThat(result.status(), is(ACTIVE));

    result = game.move(ONE, 4);
    actual = GamePrint.board(result.board());
    assertThat(actual, is("""
                     Player Two
           | 07 | 08 | 08 | 08 | 09 | 01 |
      (01)                                 (03)
           | 01 | 00 | 08 | 00 | 09 | 09 |
                     Player One
      """));
    assertThat(result.next(), is(TWO));
    assertThat(result.status(), is(ACTIVE));


  }

  @Test
  void shouldCaptureOppositeWhenLandingInOwnEmptyHouse() throws IllegalAccessException {

    var board = boardServiceImpl.from(
      List.of(4, 6, 6, 6, 0, 8),
      2,
      List.of(6, 6, 6, 6, 6, 6),
      0
    );
    GameService game = GameServiceImpl.create(board);
    var actual = GamePrint.board(board);

    assertThat(actual, is("""
                     Player Two
           | 06 | 06 | 06 | 06 | 06 | 06 |
      (00)                                 (02)
           | 04 | 06 | 06 | 06 | 00 | 08 |
                     Player One
      """));

    Result result = game.move(ONE, 1);
    actual = GamePrint.board(result.board());
    assertThat(actual, is("""
                     Player Two
           | 06 | 06 | 06 | 06 | 00 | 06 |
      (00)                                 (09)
           | 00 | 07 | 07 | 07 | 00 | 08 |
                     Player One
        """));

  }

  @Test
  void shouldFinishGameWhenOnePlayerHasNoSeeds() throws IllegalAccessException {
    var board = boardServiceImpl.from(
      List.of(0, 0, 0, 0, 0, 1),
      35,
      List.of(6, 6, 6, 6, 6, 5),
      0
    );
    GameService game = GameServiceImpl.create(board);
    var actual = GamePrint.board(board);
    assertThat(actual, is("""
                     Player Two
           | 05 | 06 | 06 | 06 | 06 | 06 |
      (00)                                 (35)
           | 00 | 00 | 00 | 00 | 00 | 01 |
                     Player One
      """));
    Result result = game.move(ONE, 6);
    Players players = board.getPlayers();
    assertThat(players.player1().score(), is(36));
    assertThat(players.player2().score(), is(35));
    assertThat(result.status(), is(Status.PLAYER_ONE_WIN));
  }
}
