package com.boardgames.engine;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import com.boardgames.engine.map.SquareMap;
import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.piece.NullGamePiece;
import com.boardgames.engine.piece.chess.Bishop;
import com.boardgames.engine.piece.chess.King;
import com.boardgames.engine.piece.chess.Knight;
import com.boardgames.engine.piece.chess.Pawn;
import com.boardgames.engine.piece.chess.Queen;
import com.boardgames.engine.piece.chess.Rook;
import com.boardgames.engine.player.AbstractGamePlayer;
import com.boardgames.engine.player.ChessPlayer;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class ChessGame extends AbstractGameEngine {
	
	AbstractGamePiece pieceCache;
	Vector<Point> movementCache;

	
	public ChessGame(){
		
		partialTurnMessage = " please enter your move (i.e \"a3-b4\"): ";
		movementCache = new Vector<>();
		pieceCache = new NullGamePiece(null);
		
	}

	@Override
	public void initializePlayers() {
		HashSet<Point> boardSouth, boardNorth;
		boardSouth = EngineUtils.createEdge(0, getMap().getNumCols(), 0, 1);
		boardNorth = EngineUtils.createEdge(0, getMap().getNumCols(), getMap().getNumRows() - 1, getMap().getNumRows());

		setCurrentPlayer(new ChessPlayer("White Player", "You are upper case", getMap(), boardNorth));
		otherPlayer = new ChessPlayer("Black Player", "You are lower case", getMap(), boardSouth);
		
		initializePlayerPieces(getCurrentPlayer(), true, 1, 0); //white player: pawns row 1, other pieces row 0
		initializePlayerPieces(otherPlayer, false, 6, 7);  //black player: pawns row 6, other pieces row 7
	}

	@Override
	public void initializeMap() {
		
		setMap(new SquareMap());

	}

	private void initializePlayerPieces(AbstractGamePlayer p, boolean movesNorth, int playerPawnRow, int playerOtherRow) {
		
		HashSet<AbstractGamePiece> temp = new HashSet<>();
		
		for(Point each: getMap().getGameMap().keySet()){
			
			if(each.y == playerPawnRow){
				
				temp.add(new Pawn(new Point(each.x, each.y), getMap(), p.getPlayerCharShift(), movesNorth));
				
			}
			
			if(each.y == playerOtherRow){
				
				if(each.x == 0 || each.x == 7){
					
					temp.add(new Rook(new Point(each.x, each.y), getMap(), p.getPlayerCharShift(), movesNorth));
					
				}
				
				if(each.x == 1 || each.x == 6){
					
					temp.add(new Knight(new Point(each.x, each.y), getMap(), p.getPlayerCharShift(), movesNorth));
					
				}
				
				if(each.x == 2 || each.x == 5){
					
					temp.add(new Bishop(new Point(each.x, each.y), getMap(), p.getPlayerCharShift(), movesNorth));
					
				}
				
				if(each.x == 4){
					
					temp.add(new King(new Point(each.x, each.y), getMap(), p.getPlayerCharShift(), movesNorth));
					
				}
				
				if(each.x == 3){
					
					temp.add(new Queen(new Point(each.x, each.y), getMap(), p.getPlayerCharShift(), movesNorth));
					
				}
				
			}
			
		}
		
		p.setPieces(temp);
		placePieces(p);
		
	}

	@Override
	public void checkWinner() {
		
		winner = otherPlayer.getCurrentMoves().isEmpty();
	}
	
	public boolean checkCheck(){
		
		otherPlayer.generateMoves();
		
		for(String each: otherPlayer.getCurrentMoves().keySet()){
			Vector<Point> temp = EngineUtils.parseSquareInput(each);
			if(Character.toLowerCase(getMap().getPiece(temp.elementAt(1)).getSymbol()) == 'k'){
				
				return true;
				
			}
		}
		
		return false;
		
	}
	public void undoLastMove(){
		getMap().swap(movementCache.elementAt(1), movementCache.elementAt(0));
		
		Point temp = getMap().getPiece(movementCache.elementAt(0)).getLocation();
		getMap().getPiece(movementCache.elementAt(0)).setLocation(getMap().getPiece(movementCache.elementAt(1)).getLocation());
		getMap().getPiece(movementCache.elementAt(1)).setLocation(temp);
		
		if(!getMap().getPiece(movementCache.elementAt(0)).isFirstMove()){
			getMap().getPiece(movementCache.elementAt(0)).setFirstMove(true);
		}
		
		if(!pieceCache.isEmpty()){
			otherPlayer.addPiece(pieceCache);
			getMap().getCell(pieceCache.getLocation()).addPiece(pieceCache);
		}
		
	}

	@Override
	public void findForceMoves() {
		
		int firstRank;
		
		if(currentPlayer.getName().equals("White Player")){
			firstRank = 0;
		}else{
			firstRank = 7;
		}
		checkCastling(currentPlayer, firstRank);
		checkEnPassant();
		
		HashMap<String, Command> forceMoves = new HashMap<>();
		AbstractGamePiece temp = pieceCache;
		pieceCache = new NullGamePiece(null);
		if(checkCheck()){
			for(String each: getCurrentPlayer().getCurrentMoves().keySet()){
				getCurrentPlayer().getMoveFactory().executeCommand(each);
				if(!checkCheck()){
					forceMoves.put(each, getCurrentPlayer().getCurrentMoves().get(each));
				}
				undoLastMove();
			}

			getCurrentPlayer().setCurrentMoves(forceMoves);
			
		}
		pieceCache = temp;
	}
	
	@Override
	public void processTurn(String s) {
		
		int firstRank;
		
		if(currentPlayer.getName().equals("White Player")){
			firstRank = 1;
		}else{
			firstRank = 8;
		}
		
		if(s.equals("0-0")){
			processTurn("e" + firstRank + "-" + "g" + firstRank);
			processTurn("h" + firstRank + "-" + "f" + firstRank);
		}else if(s.equals("0-0-0")){
			processTurn("e" + firstRank + "-" + "c" + firstRank);
			processTurn("a" + firstRank + "-" + "d" + firstRank);
		}else if(s.charAt(2) == 'x'){
			
			String enPassant = s.substring(0,2) + '-' + s.substring(3);
			processTurn(enPassant);
			
			Vector<Point> movements;
			movements = EngineUtils.parseSquareInput(enPassant);
			
			Point enPassantAttacked = new Point(movements.elementAt(1).x, movements.elementAt(0).y);
			
			pieceCache = getMap().getPiece(enPassantAttacked);
			otherPlayer.removePiece(enPassantAttacked);
			getMap().getCell(enPassantAttacked).emptyPiece();
		
		}else{
			
	
			Vector<Point> movements;
			movements = EngineUtils.parseSquareInput(s);
			movementCache = movements;

			
			if(!getMap().getCell(movements.elementAt(1)).isEmpty()){ //move generator already checked the logic
				pieceCache = getMap().getPiece(movements.elementAt(1));
				otherPlayer.removePiece(movements.elementAt(1));
				getMap().getCell(movements.elementAt(1)).emptyPiece();
			}
			
			getMap().swap(movements.elementAt(0), movements.elementAt(1));
			
			Point temp = getMap().getPiece(movements.elementAt(0)).getLocation();
			getMap().getPiece(movements.elementAt(0)).setLocation(getMap().getPiece(movements.elementAt(1)).getLocation());
			getMap().getPiece(movements.elementAt(1)).setLocation(temp);
			
			if(getMap().getPiece(movements.elementAt(1)).isFirstMove()){
				getMap().getPiece(movements.elementAt(1)).setFirstMove(false);
			}
		}
		
		validForceMove = false;

	}

    //The king and the chosen rook are on the player's first rank. +
	//Neither the king nor the chosen rook has previously moved. +
	//There are no pieces between the king and the chosen rook. 
	//The king is not currently in check. +
	//The king does not pass through a square that is attacked by an enemy piece.
	// The king does not end up in check. (True of any legal move.) +

	public void checkCastling(AbstractGamePlayer p, int playerFirstRank){
		AbstractGamePiece queenSideRook = map.getPiece(new Point(0, playerFirstRank));
		AbstractGamePiece kingSideRook = map.getPiece(new Point(7, playerFirstRank));
		AbstractGamePiece king =  map.getPiece(new Point(4, playerFirstRank));
		
		//QueenSide
		if(Character.toLowerCase(king.getSymbol()) == 'k' && king.isFirstMove() 
		&& Character.toLowerCase(queenSideRook.getSymbol()) == 'r' && queenSideRook.isFirstMove() 
		&& !checkCheck()){
			
			int dx = -1;
			int dy = 0;
			Point index = new Point(king.getLocation().x + dx, king.getLocation().y + dy);
			while(map.getPiece(index).isEmpty() && !isUnderAttack(index)){
				index = new Point(index.x + dx, index.y + dy);
			}
			if(Character.toLowerCase(map.getPiece(index).getSymbol()) == 'r'){
				p.addMove("0-0-0");
			}
			
		}
		
		//KingSide
		if(Character.toLowerCase(king.getSymbol()) == 'k' && king.isFirstMove() 
		&& Character.toLowerCase(kingSideRook.getSymbol()) == 'r' && kingSideRook.isFirstMove() 
		&& !checkCheck()){
			
			int dx = +1;
			int dy = 0;
			Point index = new Point(king.getLocation().x + dx, king.getLocation().y + dy);
			while(map.getPiece(index).isEmpty() && !isUnderAttack(index) && map.getGameMap().containsKey(index)){
				index = new Point(index.x + dx, index.y + dy);
			}
			if(Character.toLowerCase(map.getPiece(index).getSymbol()) == 'r'){
				p.addMove("0-0");
			}
			
		}
	}
	
	
	public void checkEnPassant(){
		if(!movementCache.isEmpty()){
			
			int dy, secondRank;
			int enprow = 0;
			
			//should be the other players second rank
			if(currentPlayer.getName().equals("White Player")){
				dy = 1;
				secondRank = 6;
				enprow = 4;
			}else{
				dy = -1;
				secondRank = 1;
				enprow = 3;
			}
			
			// If the last moved piece was a pawn, from the enemies starting rank
			if(Character.toLowerCase(map.getPiece(movementCache.elementAt(1)).getSymbol()) == 'p' && 
					                 movementCache.elementAt(0).y == secondRank){
				
				//The enPassantPawn, is the pawn that can be captured for passing
				//TODO possible out of bounds error?
				Point enPassantPawn = new Point(movementCache.elementAt(0).x, enprow);
				Point enPassantCapture = new Point(enPassantPawn.x, enPassantPawn.y + dy);
				Point enPassantRight = new Point(enPassantPawn.x + 1, enprow); 
				Point enPassantLeft = new Point(enPassantPawn.x - 1, enprow);
				
				if (enPassantRight.x <= 7 &&
					Character.toLowerCase(map.getPiece(enPassantRight).getSymbol()) == 'p') {
					currentPlayer.addMove(EngineUtils.pointToString(enPassantRight) + "x" + EngineUtils.pointToString(enPassantCapture));
				}
				
				if (enPassantLeft.x >= 0 &&
					Character.toLowerCase(map.getPiece(enPassantLeft).getSymbol()) == 'p') {
					currentPlayer.addMove(EngineUtils.pointToString(enPassantLeft) + "x" + EngineUtils.pointToString(enPassantCapture));
				}
			}
		}
	}

	private boolean isUnderAttack(Point index) {
		otherPlayer.generateMoves();
		
		for(String each: otherPlayer.getCurrentMoves().keySet()){
			Vector<Point> temp = EngineUtils.parseSquareInput(each);
			if(temp.elementAt(1).equals(index)){
				
				return true;
				
			}
		}
		
		return false;
	}
}
