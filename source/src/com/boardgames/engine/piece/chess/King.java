package com.boardgames.engine.piece.chess;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class King extends ChessPiece {

	public King(Point location, AbstractGameMap map, int playerCharShift, boolean movesNorth) {
		super(EngineUtils.shiftChar('k', playerCharShift), location, map, playerCharShift, movesNorth);
	}

	@Override
	public HashMap<String, Command> generateMoves() {
		
		HashMap<String, Command> result = new HashMap<>();
		
		HashSet<HashSet<Point>> movementSets = new HashSet<>();
		
		movementSets.add(collisionLineNorth(location, 1));
		movementSets.add(collisionLineSouth(location, 1));
		movementSets.add(collisionLineEast(location, 1));
		movementSets.add(collisionLineWest(location, 1));
		movementSets.add(collisionLineSE(location, 1));
		movementSets.add(collisionLineSW(location, 1));
		movementSets.add(collisionLineNE(location, 1));
		movementSets.add(collisionLineNW(location, 1));
		
		result.putAll(pushSets(movementSets, location));
		
		return result;
		
	}

}
