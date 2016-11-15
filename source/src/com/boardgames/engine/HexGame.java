package com.boardgames.engine;
import java.awt.Point;
import java.util.HashSet;

import com.boardgames.engine.map.HexMap;
import com.boardgames.engine.piece.HexPiece;
import com.boardgames.engine.player.HexPlayer;
import com.boardgames.engine.utils.EngineUtils;

public class HexGame extends AbstractGameEngine {
	
	static int boardSize = 11;
	
	public HexGame(){
		
		super();
		
	}
	
	@Override
	public void initializePlayers() {
		
		HashSet<Point> boardWest, boardEast, boardSouth, boardNorth;
		boardWest = EngineUtils.createEdge(0, 1, 0, getMap().getNumRows());
		boardEast = EngineUtils.createEdge(getMap().getNumCols() - 1, getMap().getNumCols(), 0, getMap().getNumRows());
		boardSouth = EngineUtils.createEdge(0, getMap().getNumCols(), 0, 1);
		boardNorth = EngineUtils.createEdge(0, getMap().getNumCols(), getMap().getNumRows() - 1, getMap().getNumRows());
		
		setCurrentPlayer(new HexPlayer("Red Player", new HexPiece('X', null, getMap()), "Try to span vertically.", getMap(), boardSouth, boardNorth));
		otherPlayer = new HexPlayer("Blue Player", new HexPiece('O', null, getMap()), "Try to span horizontally", getMap(), boardWest, boardEast);
		
	}
	
	@Override
	public void initializeMap() {

		setMap(new HexMap());

	}
	
	@Override
	public void checkWinner() {
		
		winner = getCurrentPlayer().checkWinner();

	}

	@Override
	public void findForceMoves() {}

	@Override
	public void processTurn(String s) {
		getMap().placePiece(EngineUtils.parseInput(s), getCurrentPlayer().getPiece());
		validForceMove = false;
	}
}
