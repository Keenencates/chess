package com.boardgames.engine.piece.chess;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.EngineUtils;

public class Knight extends ChessPiece {

	public Knight(Point location, AbstractGameMap map, int playerCharShift, boolean movesNorth) {
		super(EngineUtils.shiftChar('n', playerCharShift), location, map, playerCharShift, movesNorth);
	}

	@Override
	public HashMap<String, Command> generateMoves() {
		
		HashMap<String, Command> result = new HashMap<>();
		
		HashSet<HashSet<Point>> movementSets = new HashSet<>();
		
		movementSets.add(collisionKnights(location));
		
		result.putAll(pushSets(movementSets, location));
		
		return result;
	}

}
