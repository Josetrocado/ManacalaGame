package com.example.kalaha.mancalagame;

import com.example.kalaha.mancalagame.domain.*;
import com.example.kalaha.mancalagame.service.BoardServiceImpl;
import com.example.kalaha.mancalagame.service.GameServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MancalaGameApplication {

	public static void main(String[] args) throws IllegalAccessException {
		SpringApplication.run(MancalaGameApplication.class, args);

    Scanner scan = new Scanner(System.in);
    BoardServiceImpl boardServiceImpl = new BoardServiceImpl();
    Board board = boardServiceImpl.createBoard();
    GameServiceImpl game = GameServiceImpl.create(board);
    System.out.println(GamePrint.board(board));
    System.out.println("Select house::  ");
    int selectedHouse = scan.nextInt();

    Result result =  game.move(PlayerNumber.ONE,selectedHouse);
    System.out.println(GamePrint.board(board));
    while (result.status() == Status.ACTIVE){
      System.out.println("Player " +result.next() + " turn");

      System.out.println("Select house:: ");
      selectedHouse = scan.nextInt();
      if (result.next() == PlayerNumber.ONE) {
        result =  game.move(PlayerNumber.ONE,selectedHouse);
        System.out.println(GamePrint.board(result.board()));
      } else if (result.next() == PlayerNumber.TWO) {
        result =  game.move(PlayerNumber.TWO,selectedHouse);
        System.out.println(GamePrint.board(result.board()));
      }
    }
    if (result.status() == Status.PLAYER_ONE_WIN) {
      System.out.println("Player one WINS!!");
    } else if (result.status() == Status.PLAYER_TWO_WIN){
      System.out.println("Player two WINS!!");
    } else if (result.status() == Status.DRAW){
      System.out.println("IT's a draw");
    }
	}

}
