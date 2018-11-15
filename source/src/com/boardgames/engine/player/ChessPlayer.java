package com.boardgames.engine.player;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.map.SquareMap;
import com.boardgames.engine.ChessGame;
import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.piece.NullGamePiece;
import com.boardgames.engine.piece.chess.Queen;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.CommandFactory;

public class ChessPlayer extends AbstractGamePlayer {
	
	HashSet<Point> zone1; //The zone where normal pieces become Queens
	boolean movesNorth;

	public ChessPlayer(String name, String turnMessage, AbstractGameMap map, HashSet<Point> zone1){
		
		super(name, new NullGamePiece(null), turnMessage, map);
		this.zone1 = zone1;
		
		if(name == "Black Player"){
			playerCharShift = 0;
			movesNorth = false;
		}
		if(name == "White Player"){
			playerCharShift = 'A' - 'a';
			movesNorth = true;
		}
		
	}
	
	public ChessPlayer(AbstractGamePlayer p) {
		
		this(p.name, p.turnMessage, new SquareMap(), new HashSet<>());
		this.moveFactory = new CommandFactory();
		moveFactory.getCommands().putAll(p.moveFactory.getCommands());
		this.pieces = new HashSet<>();
		pieces.addAll(p.getPieces());
		this.playerCharShift = p.playerCharShift;
	}

	@Override
	public void checkPromotions(){
		for(Point each: map.getGameMap().keySet()){
			
			if(zone1.contains(each) && Character.toLowerCase(map.getPiece(each).getSymbol()) == 'p'){
				AbstractGamePiece q = new Queen(each, map, playerCharShift, movesNorth);
				pieces.remove(each);
				pieces.add(q);
				map.getCell(each).addPiece(q);
			}
			
		}
	}
	
}
