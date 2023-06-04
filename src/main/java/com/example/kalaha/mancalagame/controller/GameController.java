package com.example.kalaha.mancalagame.controller;


import com.example.kalaha.mancalagame.domain.Board;
import com.example.kalaha.mancalagame.domain.PlayerNumber;
import com.example.kalaha.mancalagame.domain.Result;
import com.example.kalaha.mancalagame.service.BoardService;
import com.example.kalaha.mancalagame.service.BoardServiceImpl;
import com.example.kalaha.mancalagame.service.GameService;
import com.example.kalaha.mancalagame.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manacala")
@CrossOrigin(origins = "http://localhost:4200/", methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = {"Content-Type", "Authorization"})
public class GameController {
  @Autowired
  private BoardService boardServiceImpl;

  @Autowired
  private GameService gameServiceImpl;


  public GameController(BoardServiceImpl boardServiceImpl, GameServiceImpl gameServiceImpl) {
    this.boardServiceImpl = boardServiceImpl;
    this.gameServiceImpl = gameServiceImpl;
  }


  @GetMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Board getBoard() {
    Board board = boardServiceImpl.createBoard();
    gameServiceImpl = GameServiceImpl.create(board);
    return board;
  }

  @PostMapping(value = "/move/{playerNumber}/{house}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String makeMove(@PathVariable String playerNumber, @PathVariable int house) throws IllegalAccessException {
    PlayerNumber player = gameServiceImpl.determinePlayer(playerNumber);
    Result result = gameServiceImpl.move(player, house);
    return gameServiceImpl.generateBoardString(result);
  }

}
