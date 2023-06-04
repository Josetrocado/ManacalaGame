package com.example.kalaha.mancalagame.controller;


import com.example.kalaha.mancalagame.domain.Board;
import com.example.kalaha.mancalagame.domain.GamePrint;
import com.example.kalaha.mancalagame.domain.PlayerNumber;
import com.example.kalaha.mancalagame.domain.Result;
import com.example.kalaha.mancalagame.service.BoardServiceImpl;
import com.example.kalaha.mancalagame.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manacala")
@CrossOrigin(origins = "http://localhost:4200/",
  methods = {RequestMethod.GET, RequestMethod.POST},
  allowedHeaders = {"Content-Type", "Authorization"})
public class GameController {

  public GameController(BoardServiceImpl boardServiceImpl, GameServiceImpl gameServiceImpl) {
    this.boardServiceImpl = boardServiceImpl;
    this.gameServiceImpl = gameServiceImpl;
  }

  @Autowired
  private BoardServiceImpl boardServiceImpl;

  @Autowired
  private GameServiceImpl gameServiceImpl;


  @GetMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Board getBoard() {
   Board board = boardServiceImpl.createBoard();
   gameServiceImpl = GameServiceImpl.create(board);
//   return "Hi";
    return board;
  }

  @PostMapping(value = "/move/{playerNumber}/{house}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String makeMove(@PathVariable String playerNumber, @PathVariable int house) throws IllegalAccessException {
   PlayerNumber player = determinePlayer(playerNumber);

   Result result = gameServiceImpl.move(player, house);
   return generateBoardString(result);
  }


  // Add to a service
  private PlayerNumber determinePlayer(String playerNumber) {
    if (playerNumber.equals("ONE")) {
      return PlayerNumber.ONE;
    } return PlayerNumber.TWO;
  }

  private String generateBoardString(Result result) {
    return GamePrint.board(result.board());
  }

  public String getStartingBoard(){

   return """
                    Player Two
          | 06 | 06 | 06 | 06 | 06 | 06 |
     (00)                                 (00)
          | 06 | 06 | 06 | 06 | 06 | 06 |
                    Player One
     """;
  }

}
