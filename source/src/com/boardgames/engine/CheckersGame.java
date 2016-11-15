package com.boardgames.engine;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import com.boardgames.engine.map.SquareMap;
import com.boardgames.engine.piece.CheckersPiece;
import com.boardgames.engine.player.CheckersPlayer;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class CheckersGame extends AbstractGameEngine {

	public CheckersGame(){

		super();
		partialTurnMessage = " please enter your move (i.e \"a2-b3\"): ";
		
	}
	
	@Override
	public void initializePlayers() {
		
		HashSet<Point> boardSouth, boardNorth;
		boardSouth = EngineUtils.createEdge(0, getMap().getNumCols(), 0, 1);
		boardNorth = EngineUtils.createEdge(0, getMap().getNumCols(), getMap().getNumRows() - 1, getMap().getNumRows());
		
		setCurrentPlayer(new CheckersPlayer("White Player", new CheckersPiece('o', null, getMap(), true, false), "o is pawn, O is king", getMap(), boardNorth));
		otherPlayer = new CheckersPlayer("Black Player", new CheckersPiece('x', null, getMap(), false, true), "x is pawn, X is king", getMap(), boardSouth);
		
		initializeWhite();
		initializeBlack();

	}

	@Override
	public void initializeMap() {
		
		setMap(new SquareMap());

	}

	@Override
	public void checkWinner() {
		
		otherPlayer.clearMoves();
		otherPlayer.generateMoves();
		
		winner = otherPlayer.getCurrentMoves().isEmpty();

	}
	
	private void initializeWhite(){
		
		for(Point each: getMap().getGameMap().keySet()){
			
			if(((each.y % 2 == 0) && (each.x % 2 == 0) && (each.y < 3)) || ((each.y == 1) && (each.x % 2 != 0))){
				
				getCurrentPlayer().getPieces().add(new CheckersPiece('o', new Point(each.x, each.y), getMap(), true, false));
				
			}
		}
		
		placePieces(getCurrentPlayer());
		
	}
	
	private void initializeBlack(){
		
		for(Point each: getMap().getGameMap().keySet()){
			
			if(((each.y % 2 == 0) && (each.x % 2 == 0) && (each.y > 4)) || ((each.y == 5) && (each.x % 2 != 0)) 
					|| ((each.y == 7) && (each.x % 2 != 0))){
				
				otherPlayer.getPieces().add(new CheckersPiece('x', new Point(each.x, each.y), getMap(), false, true));
				
			}
		}
		
		placePieces(otherPlayer);
		
	}

	@Override
	public void findForceMoves() {
		
		HashMap<String, Command> forceMoves = new HashMap<>();
		
		for(String each: getCurrentPlayer().getCurrentMoves().keySet()){
			Vector<Point> temp = EngineUtils.parseSquareInput(each);
			if(getMap().getCell(temp.elementAt(0)).getDistance(getMap().getCell(temp.elementAt(1))) == 2){
				
				forceMoves.put(each, getCurrentPlayer().getCurrentMoves().get(each));
				
			}
		}
		
		for(String each: forceMoves.keySet()){
			if(!getCurrentPlayer().getCurrentMoves().containsKey(each)){
				getCurrentPlayer().getCurrentMoves().remove(each);
			}
		}
	}
	
	@Override
	public void processTurn(String s) {
		Vector<Point> movements;
		movements = EngineUtils.parseSquareInput(s);
		
		getMap().swap(movements.elementAt(0), movements.elementAt(1));
		
		//If the jump is 2 spaces, then the middle piece should be removed. the generate moves list already deals with the logic
		if(getMap().getCell(movements.elementAt(0)).getDistance(getMap().getCell(movements.elementAt(1))) == 2){
			
			Point middlePiece =  new Point((Math.abs((movements.elementAt(1).x + movements.elementAt(0).x)/2)), 
					(Math.abs(movements.elementAt(1).y + movements.elementAt(0).y)/2));
			getMap().getCell(middlePiece).emptyPiece();
			otherPlayer.getPieces().remove(middlePiece);
			
			validForceMove = true;
		}
		
		else{
			validForceMove = false;
		}
		
		Point temp = getMap().getPiece(movements.elementAt(0)).getLocation();
		getMap().getPiece(movements.elementAt(0)).setLocation(getMap().getPiece(movements.elementAt(1)).getLocation());
		getMap().getPiece(movements.elementAt(1)).setLocation(temp);

	}
}
