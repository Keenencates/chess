package com.boardgames.engine.player;
import java.awt.Point;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.piece.AbstractGamePiece;

public class CheckersPlayer extends AbstractGamePlayer {
	
	boolean validForce;
	HashSet<Point> zone1; //The zone where normal pieces become kings
	
	public CheckersPlayer(String name, AbstractGamePiece piece, String turnMessage, AbstractGameMap map, HashSet<Point> zone1){
		
		super(name, piece, turnMessage, map);
		this.zone1 = zone1;
		this.validForce = false;
		
	}

	@Override
	public void checkPromotions(){
		for(Point each: map.getGameMap().keySet()){
			
			if(zone1.contains(each) && map.getPiece(each).getSymbol() == piece.getSymbol()){
				map.getPiece(each).setSymbol(Character.toUpperCase(map.getPiece(each).getSymbol()));
				map.getPiece(each).setMovesNorth(true);
				map.getPiece(each).setMovesSouth(true);
			}
			
		}
	}
}
