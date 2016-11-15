package com.boardgames.engine.piece.chess;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class Queen extends ChessPiece {

	public Queen(Point location, AbstractGameMap map, int playerCharShift, boolean movesNorth) {
		super(EngineUtils.shiftChar('q', playerCharShift), location, map, playerCharShift, movesNorth);
	}

	@Override
	public HashMap<String, Command> generateMoves() {
		
		HashMap<String, Command> result = new HashMap<>();
		
		HashSet<HashSet<Point>> movementSets = new HashSet<>();
		
		movementSets.add(collisionLineNorth(location, 8));
		movementSets.add(collisionLineSouth(location, 8));
		movementSets.add(collisionLineEast(location, 8));
		movementSets.add(collisionLineWest(location, 8));
		movementSets.add(collisionLineSE(location, 8));
		movementSets.add(collisionLineSW(location, 8));
		movementSets.add(collisionLineNE(location, 8));
		movementSets.add(collisionLineNW(location, 8));
		
		result.putAll(pushSets(movementSets, location));
		
		return result;
		
	}

}
