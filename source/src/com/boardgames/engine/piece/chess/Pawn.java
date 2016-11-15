package com.boardgames.engine.piece.chess;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class Pawn extends ChessPiece {

	public Pawn(Point location, AbstractGameMap map, int playerCharShift, boolean movesNorth) {
		super(EngineUtils.shiftChar('p', playerCharShift), location, map, playerCharShift, movesNorth);
	}

	@Override
	public HashMap<String, Command> generateMoves() {
		
		HashMap<String, Command> result = new HashMap<>();
		
		HashSet<HashSet<Point>> movementSets = new HashSet<>();
		
		int pawnDist = 2;
		
		if(!map.getPiece(location).isFirstMove()){
			pawnDist = 1;
		}
		
		if(movesNorth){
			movementSets.add(exclusiveLineNorth(location, pawnDist));
			movementSets.add(inclusiveLine(location, 1, 1, 1));
			movementSets.add(inclusiveLine(location, 1, -1, 1));
		}else{
			movementSets.add(exclusiveLineSouth(location, pawnDist));
			movementSets.add(inclusiveLine(location, 1, 1, -1));
			movementSets.add(inclusiveLine(location, 1, -1, -1));
		}
		
		result.putAll(pushSets(movementSets, location));
		
		return result;
		
	}

}
