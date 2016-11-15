package com.boardgames.engine.piece;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;

public class CheckersPiece extends AbstractGamePiece {
	
	public CheckersPiece(char symbol, Point p, AbstractGameMap map, boolean movesNorth, boolean movesSouth) {
		super(symbol, p, map);
		this.movesNorth = movesNorth;
		this.movesSouth = movesSouth;
		this.isEmpty = false;
	}

	@Override
	public HashMap<String, Command> generateMoves() {
		
		HashMap<String, Command> result = new HashMap<>();
		
		for(Point each: map.getGameMap().keySet()){
			
			HashSet<HashSet<Point>> movementSets = new HashSet<>();
			
			if(Character.toLowerCase(map.getPiece(each).getSymbol()) == symbol){
			
				if(map.getPiece(each).movesNorth()){
					
					movementSets.add(collisionLineNE(each, 2));
					movementSets.add(collisionLineNW(each, 2));
					
				}
				
				if(map.getPiece(each).movesSouth()){
					
					movementSets.add(collisionLineSE(each, 2));
					movementSets.add(collisionLineSW(each, 2));
					
				}
				
				result.putAll(pushSets(movementSets, each));
				
			}
		}
		
		return result;
	}
	
	@Override
	protected HashSet<Point> collisionLine(Point p, int MaxLength, int dx, int dy) {

		HashSet<Point> result =  new HashSet<>();
		
		Point index = p;
		index = new Point(index.x + dx, index.y + dy);
		
		while(map.getGameMap().containsKey(index) && map.getCell(p).getDistance(map.getCell(index)) <= MaxLength
				&& Character.toLowerCase(map.getPiece(index).getSymbol()) != symbol){
			
			
			if(!map.getCell(index).isEmpty()){
			}else{
				result.add(index);
				MaxLength -= 1;
			}
			
			index = new Point(index.x + dx, index.y + dy);
		}
		
		return result;
	}
}
