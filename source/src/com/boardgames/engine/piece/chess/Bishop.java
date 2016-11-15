package com.boardgames.engine.piece.chess;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class Bishop extends ChessPiece {

	public Bishop(Point location, AbstractGameMap map, int playerCharShift, boolean movesNorth) {
		
		super(EngineUtils.shiftChar('b', playerCharShift), location, map, playerCharShift, movesNorth);
	}

	@Override
	public HashMap<String, Command> generateMoves() {
		
		HashMap<String, Command> result = new HashMap<>();
		
		HashSet<HashSet<Point>> movementSets = new HashSet<>();
		
		movementSets.add(collisionLineSE(location, 10));
		movementSets.add(collisionLineSW(location, 10));
		movementSets.add(collisionLineNE(location, 10));
		movementSets.add(collisionLineNW(location, 10));
		
		result.putAll(pushSets(movementSets, location));
		
		return result;
		
	}

}
