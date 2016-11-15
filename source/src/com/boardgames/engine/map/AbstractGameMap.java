package com.boardgames.engine.map;
import java.awt.Point;
import java.util.HashMap;

import com.boardgames.engine.piece.AbstractGamePiece;

public abstract class AbstractGameMap{

	protected HashMap<Point, GameCell> gameMap;
	protected int numCols;
	protected int numRows;
	
	public AbstractGameMap(){
		
		this(10, 10);
		
	}
	
	public AbstractGameMap(int numCols, int numRows){
		
		this.numCols = numCols;
		this.numRows = numRows;
		initializeMap();
		
	}
	
	public HashMap<Point, GameCell> getGameMap() {
		return gameMap;
	}

	public void setGameMap(HashMap<Point, GameCell> gameMap) {
		this.gameMap = gameMap;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public void placePiece(Point p, AbstractGamePiece piece){
		
		gameMap.get(p).addPiece(piece);
		
	}
	
	public AbstractGamePiece getPiece(Point p){
		
		return gameMap.get(p).getPiece();
		
	}
	
	public GameCell getCell(Point p){
		
		return gameMap.get(p);
		
	}
	
	public abstract void printMap();
	
	public abstract void initializeMap();

	public void swap(Point p1, Point p2) {
		
		AbstractGamePiece temp1 = getPiece(p1);
		AbstractGamePiece temp2 = getPiece(p2);
		getCell(p1).addPiece(temp2);
		getCell(p2).addPiece(temp1);
		
	}
	
	
	
}
