package com.boardgames.engine.piece;
import java.awt.Point;
import java.util.HashMap;

import com.boardgames.engine.utils.Command;

public class NullGamePiece extends AbstractGamePiece {

	public NullGamePiece(Point p){
		
		super('-', p, null);
		
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public HashMap<String, Command> generateMoves() {return new HashMap<>();}
	
}
