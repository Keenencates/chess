package com.boardgames.engine.map;
import java.awt.Point;
import java.util.HashSet;

import com.boardgames.engine.piece.AbstractGamePiece;
import com.boardgames.engine.piece.NullGamePiece;

public class GameCell{

	//Game cell has a location and can hold a gamepiece.
	protected Point point;
	protected AbstractGamePiece piece;
	private HashSet<Point> hexNeighbors;
	
	boolean ignore = false; //needed for recursive check
	
	public GameCell(String name, Point point){
		
		this(point, new NullGamePiece(point));
		
	}
	
	public GameCell(Point point, AbstractGamePiece piece){
		
		this.point = point;
		this.piece = piece;
		hexNeighbors = new HashSet<>();
		generateHexNeighbors();
		
	}

	public double getX(){
		
		return point.getX();
		
	}
	
	public double getY(){
		
		return point.getY();
		
	}
	
	public AbstractGamePiece getPiece(){
		
		return piece;
		
	}
	
	public Point getPoint(){
		
		return point;
		
	}
	
	//Don't want the straight up distance. Want the max distance between coordinates since each space is one "move"
	public double getDistance(GameCell c){
		
		return Math.max(Math.abs(c.getX() - this.getX()), Math.abs(c.getY() - this.getY()));
		
	}
	
	public boolean isNeighbor(GameCell c){
		
		return (getDistance(c) == 1.0);
		
	}
	
	public void addPiece(AbstractGamePiece piece){
		
		this.piece = piece;
		
	}
	
	public void emptyPiece(){
		
		piece = new NullGamePiece(point);
		
	}
	
	public boolean isEmpty(){
		
		return piece.isEmpty();
		
	}
	
	public String toString(){
			
		return piece.toString();
			
	}

	public HashSet<Point> getNeighbors() {
		
		return hexNeighbors;
		
	}
	
	public double getZ(){
		
		return getX() + getY() * -1.0;
		
	}
	
	public void generateHexNeighbors(){
		
		hexNeighbors.add(new Point(this.point.x - 1, this.point.y));
		hexNeighbors.add(new Point(this.point.x - 1, this.point.y + 1));
		hexNeighbors.add(new Point(this.point.x, this.point.y - 1));
		hexNeighbors.add(new Point(this.point.x, this.point.y + 1));
		hexNeighbors.add(new Point(this.point.x + 1, this.point.y - 1));
		hexNeighbors.add(new Point(this.point.x + 1, this.point.y));
		
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
}
