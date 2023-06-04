package com.example.kalaha.mancalagame.controller;


import com.example.kalaha.mancalagame.domain.Board;
import com.example.kalaha.mancalagame.domain.GamePrint;
import com.example.kalaha.mancalagame.domain.PlayerNumber;
import com.example.kalaha.mancalagame.service.BoardService;
import com.example.kalaha.mancalagame.service.GameService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manacala")
@CrossOrigin(origins = "http://localhost:4200/",
  methods = {RequestMethod.GET, RequestMethod.POST},
  allowedHeaders = {"Content-Type", "Authorization"})
public class GameController {

  public GameController(BoardService boardService, GameService gameService) {
    this.boardService = boardService;
    this.gameService = gameService;
  }

  @Autowired
  private BoardService boardService;

  @Autowired
  private GameService gameService;


  @GetMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Board getBoard() {
   Board board = boardService.createBoard();
   gameService = GameService.create(board);
//   return "Hi";
    return board;
  }

  @PostMapping(value = "/move/{playerNumber}/{house}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String makeMove(@PathVariable String playerNumber, @PathVariable int house) throws IllegalAccessException {
   PlayerNumber player = determinePlayer(playerNumber);

   GameService.Result result = gameService.move(player, house);
   return generateBoardString(result);
  }


  // Add to a service
  private PlayerNumber determinePlayer(String playerNumber) {
    if (playerNumber.equals("ONE")) {
      return PlayerNumber.ONE;
    } return PlayerNumber.TWO;
  }

  private String generateBoardString(GameService.Result result) {
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
