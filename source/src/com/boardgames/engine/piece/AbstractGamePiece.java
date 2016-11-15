package com.boardgames.engine.piece;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.Command;
import com.boardgames.engine.utils.Observable;
import com.boardgames.engine.utils.EngineUtils;

public abstract class AbstractGamePiece implements Observable{

	protected char symbol;
	protected boolean firstMove;
	protected boolean movesNorth;
	protected boolean movesSouth;
	protected boolean isEmpty;
	protected Point location;
	protected AbstractGameMap map;
	
	public AbstractGamePiece(char symbol, Point p, AbstractGameMap map){
	
		this.symbol = symbol;
		firstMove = true;
		movesNorth = false;
		movesSouth = false;
		isEmpty = true;
		location = p;
		this.map = map;
		
	}
	
	public char getSymbol(){
		
		return symbol;
		
	}
	
	public String toString(){
		
		return Character.toString(symbol);
		
	}
	
	public boolean isEmpty(){
		return isEmpty;
	}

	public boolean movesNorth() {
		return movesNorth;
	}
	
	public boolean movesSouth(){
		return movesSouth;
	}
	
	public abstract HashMap<String, Command> generateMoves();
	
	protected HashSet<Point> collisionLine(Point p, int MaxLength, int dx, int dy){return new HashSet<>();} //Includes un-associated pieces, excludes associated
	protected HashSet<Point> inclusiveLine(Point p, int MaxLength, int dx, int dy){return new HashSet<>();} //Includes only moves to un-associated
	protected HashSet<Point> exclusiveLine(Point p, int MaxLength, int dx, int dy){return new HashSet<>();} //Excludes all points with a piece
	
	//COLLISION LINES FOR EASE OF USE
	
	protected HashSet<Point> collisionLineNorth(Point p, int MaxLength){
		return collisionLine(p, MaxLength, 0, 1);
	}
	
	protected HashSet<Point> collisionLineSouth(Point p, int MaxLength){
		return collisionLine(p, MaxLength, 0, -1);
	}
	
	protected HashSet<Point> collisionLineEast(Point p, int MaxLength){
		return collisionLine(p, MaxLength, 1, 0);
	}
	
	protected HashSet<Point> collisionLineWest(Point p, int MaxLength){
		return collisionLine(p, MaxLength, -1, 0);
	}
	
	protected HashSet<Point> collisionLineNW(Point p, int MaxLength){
		return collisionLine(p, MaxLength, -1, 1);
	}
	
	protected HashSet<Point> collisionLineNE(Point p, int MaxLength){ 
		return collisionLine(p, MaxLength, 1, 1);
	}
	
	protected HashSet<Point> collisionLineSW(Point p, int MaxLength){ 
		return collisionLine(p, MaxLength, -1, -1);
	}
	
	protected HashSet<Point> collisionLineSE(Point p, int MaxLength){ 
		return collisionLine(p, MaxLength, 1, -1);
	}
	
	//EXCLUSIVE LINES FOR EASE OF USE
	
	protected HashSet<Point> exclusiveLineNorth(Point p, int MaxLength){
		return exclusiveLine(p, MaxLength, 0, 1);
	}
	
	protected HashSet<Point> exclusiveLineSouth(Point p, int MaxLength){
		return exclusiveLine(p, MaxLength, 0, -1);
	}
	
	protected HashMap<String, Command> pushSets(HashSet<HashSet<Point>> MV, Point p){				
		
		HashMap<String, Command> result = new HashMap<>();
		
		for(HashSet<Point> each: MV){
			
			for(Point each2: each){
				String s = EngineUtils.pointToString(p) + '-' + EngineUtils.pointToString(each2);
				result.put(s, () -> {});
			}
		}
		
		return result;
		
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	public boolean isMovesNorth() {
		return movesNorth;
	}

	public void setMovesNorth(boolean movesNorth) {
		this.movesNorth = movesNorth;
	}

	public boolean isMovesSouth() {
		return movesSouth;
	}

	public void setMovesSouth(boolean movesSouth) {
		this.movesSouth = movesSouth;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public AbstractGameMap getMap() {
		return map;
	}

	public void setMap(AbstractGameMap map) {
		this.map = map;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	
}
