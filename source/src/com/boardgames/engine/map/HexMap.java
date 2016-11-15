package com.boardgames.engine.map;
import java.awt.Point;
import java.util.HashMap;

public class HexMap extends AbstractGameMap {
	
	protected static int boardSize = 11;
	
	public HexMap(){
		
		super(boardSize, boardSize);
		
	}

	@Override
	public void printMap() {
		
		String oString;
	
		for(int i = numRows - 1; i >= 0; i--){
			
			int numSpaces = i + 1;
			int x1 = i + 1;
			oString = String.format("%" + numSpaces + "s", Integer.toString(x1)) + " ";
			
			for(int j = 0; j < numCols; j++){
				
				oString += (gameMap.get(new Point(j, i)).toString() + " ");
				
			}
			
			System.out.println(oString);
			
		}
		
		System.out.print("  ");
		char key = 'a';
		for(int i = 0; i < numCols; i++){
			
			System.out.print(key + " ");
			key++;
			
		}
		System.out.println("");

	}
	
	@Override
	public void initializeMap() {
		
		gameMap = new HashMap<>();
		
		for(int i = 0; i < numRows; i++){
			
			for(int j = 0; j < numCols; j++){
				
				Point point = new Point(j, i);
				char index = 'a'; 
				index += j;
				String name = Character.toString(index) + Integer.toString(i);
				gameMap.put(point,  new GameCell(name, point));
				
			}
			
		}

	}

}
