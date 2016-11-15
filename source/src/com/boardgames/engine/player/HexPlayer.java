package com.boardgames.engine.player;
import java.awt.Point;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.utils.EngineUtils;

public class HexPlayer extends AbstractGamePlayer {
	
	HashSet<Point> zone1;
	HashSet<Point> zone2;

	public HexPlayer(String name, AbstractGamePiece piece, String turnMessage, 
			AbstractGameMap map, HashSet<Point> zone1, HashSet<Point> zone2){
		
		super(name, piece, turnMessage, map);
		this.zone1 = zone1;
		this.zone2 = zone2;
		
	}
	
	@Override
	public void generateMoves() {

		clearMoves();
		
		for(Point each: map.getGameMap().keySet()){
			
			if(map.getCell(each).isEmpty()){
				
				String s = EngineUtils.pointToString(each);
				moveFactory.addCommand(s, () -> {});
				
			}
			
		}

	}
	
	@Override
	public boolean checkWinner() {
		
		return isSpanning();
		
	}

	private boolean isSpanning() {
		
		for(Point each: zone1){
			
			if(reaches(each)){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	private boolean reaches(Point p){
		
		if(zone2.contains(p)){
			
			return true;
			
		}
		
		if(!isNeighbor(p)){
			
			return false;
			
		}
		
		map.getCell(p).setIgnore(true);
		
		for(Point each: map.getCell(p).getNeighbors()){
			
			if(isNeighbor(each)){
				if(reaches(each)){
					return true;
				}
			}
			
		}
		
		map.getCell(p).setIgnore(false);
		return false;
		
	}
}
