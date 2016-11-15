package com.boardgames.engine.piece.chess;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.utils.Command;

public abstract class ChessPiece extends AbstractGamePiece {

	int playerCharShift;
	
	public ChessPiece(char symbol, Point location, AbstractGameMap map, int playerCharShift, boolean movesNorth) {
		super(symbol, location, map);
		this.isEmpty = false;
		this.playerCharShift = playerCharShift;
		this.movesNorth = movesNorth;
	}

	@Override
	public abstract HashMap<String, Command> generateMoves();
	
	protected HashSet<Point> exclusiveLine(Point p, int MaxLength, int dx, int dy){
		HashSet<Point> result =  new HashSet<>();
		HashSet<Point> slatedRemoval = new HashSet<>();
		result = collisionLine(p, MaxLength, dx, dy);
		
		for(Point each: result){
			
			if(!map.getCell(each).isEmpty()){
				
				slatedRemoval.add(each);
				
			}
			
		}
		
		for(Point each: slatedRemoval){
			
			result.remove(each);
			
		}
		
		return result;
	}
	
	protected HashSet<Point> inclusiveLine(Point p, int MaxLength, int dx, int dy){
		HashSet<Point> result =  new HashSet<>();
		HashSet<Point> slatedRemoval = new HashSet<>();
		result = collisionLine(p, MaxLength, dx, dy);
		
		for(Point each: result){
			
			if(map.getCell(each).isEmpty()){
				
				slatedRemoval.add(each);
				
			}
			
		}
		
		for(Point each: slatedRemoval){
			
			result.remove(each);
			
		}
		
		return result;
	}
	
	//Trying to figure out a way to create a singular collision line function.
	
	protected HashSet<Point> collisionLine(Point p, int MaxLength, int dx, int dy){ //creates a line of points(based on slope) that collides with units. 
												   //Includes enemy units excludes friendly units;
		HashSet<Point> result =  new HashSet<>();
		
		boolean go = true;
		
		Point index = p;
		index = new Point(index.x + dx, index.y + dy);
		
		while(go && map.getGameMap().containsKey(index) && map.getCell(p).getDistance(map.getCell(index)) <= MaxLength
				&& map.getPiece(index).getSymbol() != 'p' + playerCharShift && map.getPiece(index).getSymbol() != 'q' + playerCharShift 
				&& map.getPiece(index).getSymbol() != 'k' + playerCharShift && map.getPiece(index).getSymbol() != 'r' + playerCharShift
				&& map.getPiece(index).getSymbol() != 'n' + playerCharShift && map.getPiece(index).getSymbol() != 'b' + playerCharShift){
			
			result.add(index);
			
			if(	      (Character.toLowerCase(map.getPiece(index).getSymbol()) == 'p' || Character.toLowerCase(map.getPiece(index).getSymbol()) == 'r'
					|| Character.toLowerCase(map.getPiece(index).getSymbol()) == 'k' || Character.toLowerCase(map.getPiece(index).getSymbol()) == 'q'
					|| Character.toLowerCase(map.getPiece(index).getSymbol()) == 'n' || Character.toLowerCase(map.getPiece(index).getSymbol()) == 'b')){
				go = false;
			}
			if(go){
				index = new Point(index.x + dx, index.y + dy);
			}
		}
		
		return result;
	}
	
	protected HashSet<Point> collisionKnights(Point p){
		HashSet<Point> result = new HashSet<>();
		
		result.addAll(collisionLine(p, 2, -2, -1));
		result.addAll(collisionLine(p, 2, -2, 1));
		result.addAll(collisionLine(p, 2, 2, -1));
		result.addAll(collisionLine(p, 2, 2, 1));
		result.addAll(collisionLine(p, 2, -1, -2));
		result.addAll(collisionLine(p, 2, -1, 2));
		result.addAll(collisionLine(p, 2, 1, -2));
		result.addAll(collisionLine(p, 2, 1, 2));
		
		return result;
	}
}
