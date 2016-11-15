package com.boardgames;
import java.awt.EventQueue;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.boardgames.engine.AbstractGameEngine;
import com.boardgames.engine.CheckersGame;
import com.boardgames.engine.ChessGame;
import com.boardgames.engine.HexGame;
import com.boardgames.engine.utils.CommandFactory;
import com.boardgames.gui.BoardGameWindow;

public class MainGame {

	AbstractGameEngine game;
	
	public static void main(String args[]){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardGameWindow window = new BoardGameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		MainGame mainGame = new MainGame();
		mainGame.runMenu();
	}
	
	public void runMenu(){
		
		boolean userContinue = true;
		Scanner menu = new Scanner(System.in);
		String menuChoice;
		CommandFactory cf = new CommandFactory();
		cf.addCommand("1", () -> {game = new HexGame();});
		cf.addCommand("2", () -> {game = new CheckersGame();});
		cf.addCommand("3", () -> { game = new ChessGame();;});
		cf.addCommand("0", () -> {System.exit(0);});
		while(userContinue){
			try{
				System.out.println("BOARD GAME LIBRARY - KEENEN CATES");
				System.out.println("1. Hex");
				System.out.println("2. Checkers");
				System.out.println("3. Chess");
				System.out.println("0 to quit");
				System.out.print("Please input the number of the game you wish to play: ");
				menuChoice = menu.nextLine();
				if(!cf.getCommands().containsKey(menuChoice)){
					throw new InputMismatchException();
				}else{
					cf.executeCommand(menuChoice);	
				}
				userContinue = false;				
			}catch(InputMismatchException e){
				System.out.println("Invalid input, please try again.");
				userContinue = true;
			}
		}
		game.runGame();
		menu.close();
	}
	
}
