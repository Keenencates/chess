package com.boardgames.engine;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.player.AbstractGamePlayer;
import com.boardgames.engine.utils.Command;

public abstract class AbstractGameEngine{

	protected AbstractGameMap map;
	protected AbstractGamePlayer currentPlayer;
	protected AbstractGamePlayer otherPlayer;
	protected boolean winner, validForceMove;
	
	String partialTurnMessage;
	
	public AbstractGameEngine(){
		
		initializeMap();
		initializePlayers();
		winner = false;
		
		partialTurnMessage = " please enter your move (i.e \"a1\"): ";
		
	}
	
	public void runGame(){
		
		Scanner scanner = new Scanner(System.in);
		
		while(!winner){
			makeMove(scanner);
			checkWinner();
			swapPlayer();
			
		}
		
		System.out.println("Congrats, " + otherPlayer.getName() + " has won.");
		scanner.close();
		
	}

	public void swapPlayer(){
		
		AbstractGamePlayer temp = getCurrentPlayer();
		setCurrentPlayer(otherPlayer);
		otherPlayer = temp;
		
	}
	
	public abstract void initializePlayers();
	
	public abstract void initializeMap();
	
	public abstract void checkWinner();
	
	public abstract void findForceMoves();
	
	public abstract void processTurn(String s);
	
	protected void placePieces(AbstractGamePlayer p){
		
		for(AbstractGamePiece each: p.getPieces()){
			
			getMap().getCell(each.getLocation()).addPiece(each);
			
		}
		
	}
	
	public void makeMove(Scanner s){
		
		validForceMove = true;
		
		while(validForceMove){
			initializeTurn();
			
			getCurrentPlayer().generateMoves();
			createMoves();
			
			findForceMoves();
			createMoves();
			
			if(!getCurrentPlayer().getCurrentMoves().isEmpty()){
				System.out.println("Current Moves: " + getCurrentPlayer().getCurrentMoves().keySet());
				
				boolean go = true; //used for input processing loop
				
				while(go){
					
					try{
						getCurrentPlayer().getMoveFactory().executeCommand(getUserInput(s));
						go = false;
					}catch(InputMismatchException e){
						
						System.out.println("Invalid input. Try again.");
						
					}
				}
				
				getCurrentPlayer().checkPromotions();
			}
			
			if(getCurrentPlayer().getCurrentMoves().isEmpty()){
				swapPlayer();
			}
		}
	}
	
	public void createMoves(){
		
		HashMap<String, Command> move = new HashMap<>();
	
		for(String each: getCurrentPlayer().getCurrentMoves().keySet()){
			move.put(each, () -> {processTurn(each);});
		}
		
		getCurrentPlayer().setCurrentMoves(move);
	}

	public void initializeTurn(){
		
		getMap().printMap();
		getCurrentPlayer().printTurnMessage();
		
	}
	
	public String getUserInput(Scanner scanner){
		
		boolean go = true;
		String input = "";
		
		while(go){
			
			System.out.print(getCurrentPlayer().getName() + partialTurnMessage); 
			input = scanner.nextLine();
			
			try{
				
				if(!getCurrentPlayer().getCurrentMoves().containsKey(input)){
					
					throw new InputMismatchException();
					
				}
				
				go = false;
				
			}catch(InputMismatchException e){
				
				System.out.println("Invalid move or there is a force move available, try again.");
				go = true;
				
			}
		}
		
		return input;
	}

	public AbstractGameMap getMap() {
		return map;
	}

	public void setMap(AbstractGameMap map) {
		this.map = map;
	}

	public AbstractGamePlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(AbstractGamePlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public AbstractGamePlayer getOtherPlayer() {
		return otherPlayer;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	
	
}
