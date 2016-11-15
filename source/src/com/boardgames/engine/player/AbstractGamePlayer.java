package com.boardgames.engine.player;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.CommandFactory;

public abstract class AbstractGamePlayer {

	protected String name;
	protected AbstractGamePiece piece;
	protected String turnMessage;
	protected CommandFactory moveFactory;
	protected HashSet<AbstractGamePiece> pieces;
	protected AbstractGameMap map; //player needs to see map in order to generate moves
	protected int playerCharShift;
	
	public AbstractGamePlayer(String name, AbstractGamePiece p, String turnMessage, AbstractGameMap map){
		
		this.name = name;
		this.piece = p;
		this.turnMessage = turnMessage;
		this.map = map;
		pieces = new HashSet<>();
		moveFactory = new CommandFactory();
		
	}
	
	public AbstractGamePiece getPiece(){
		
		return piece;
		
	}
	
	public String getTurnMessage(){
		
		return turnMessage;
		
	}
	
	public void printTurnMessage(){
		
		System.out.println(turnMessage);
		
	}
	
	public void clearMoves(){
		
		moveFactory.clearCommands();
		
	}
	
	public void addPiece(AbstractGamePiece p){
		pieces.add(p);
	}
	
	public void generateMoves(){
		
		clearMoves();
		
		for(AbstractGamePiece each: pieces){
			
			HashMap<String, Command> temp = each.generateMoves();
			
			for(String each2: temp.keySet()){
				
				moveFactory.addCommand(each2, temp.get(each2));
				
			}
			
		}
		
	}

	public boolean checkWinner(){return false;}
	
	public boolean isNeighbor(Point p){
		
		if(map.getGameMap().containsKey(p)){
			if(!map.getCell(p).isIgnore()){
				return map.getPiece(p).getSymbol() == piece.getSymbol();
			}
		}
		
		return false;
	}

	public void checkPromotions(){}
	
	public String getName() {
		return name;
	}

	//eclipse did this for me
	
	public HashMap<String, Command> getCurrentMoves(){
		return getMoveFactory().getCommands();
	}
	public CommandFactory getMoveFactory() {
		return moveFactory;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashSet<AbstractGamePiece> getPieces() {
		return pieces;
	}

	public void setPieces(HashSet<AbstractGamePiece> pieces) {
		this.pieces = pieces;
	}

	public AbstractGameMap getMap() {
		return map;
	}

	public void setMap(AbstractGameMap map) {
		this.map = map;
	}

	public int getPlayerCharShift() {
		return playerCharShift;
	}

	public void setPlayerCharShift(int playerCharShift) {
		this.playerCharShift = playerCharShift;
	}

	public void setPiece(AbstractGamePiece piece) {
		this.piece = piece;
	}

	public void setTurnMessage(String turnMessage) {
		this.turnMessage = turnMessage;
	}

	public void setMoveFactory(CommandFactory moveFactory) {
		this.moveFactory = moveFactory;
	}

	public void setCurrentMoves(HashMap<String, Command> move) {
		this.getMoveFactory().setCommands(move);
		
	}

	public void removePiece(Point elementAt) {
		pieces.remove(map.getPiece(elementAt));
	}

	public void addMove(String string) {
		moveFactory.addCommand(string, () -> {});
		
	}
	
	
}
