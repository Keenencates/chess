package com.boardgames.engine.piece;
import java.awt.Point;
import java.util.HashMap;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;

public class HexPiece extends AbstractGamePiece {

	public HexPiece(char symbol, Point location, AbstractGameMap map) {
		super(symbol, location, map);
		this.isEmpty = false;
	}

	@Override
	public HashMap<String, Command> generateMoves() {return new HashMap<>();}

}
