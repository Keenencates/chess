package com.boardgames.engine.utils;

import java.awt.Point;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Vector;

public class EngineUtils {
	
	private EngineUtils(){throw new RuntimeException();}
	
	//parseInput - expect a String in the format a1 and converts that string into a point. 
	//Throws an InputMismatchException if receives invalid String.
	public static Point parseInput(String s){
		
		int a, b;
		
		a = s.charAt(0) - 'a'; //shift integer by character displacement
		b = Integer.parseInt(s.substring(1)) - 1;
		return new Point(a, b);
	}
	
	//Takes a string of user input for board game of form a1-b1
	//returns a vector of points where index 0 is a1 and index 1 is b1
	public static Vector<Point> parseSquareInput(String s){
		
		Point a, b;
		
		if(s.contains("-")){
			
			String[] parts = s.split("-");
			a = parseInput(parts[0]);
			b = parseInput(parts[1]);
			Vector<Point> temp = new Vector<>();
			temp.add(a);
			temp.add(b);
			return temp;
			
		}else{

			throw new InputMismatchException();
			
		}
	}
	
	//Used to create sets of points
	public static HashSet<Point> createEdge(int init_x, int x, int init_y, int y){
		
		HashSet<Point> points = new HashSet<>();
		
		for(int i = init_x; i < x; i++){
			
			for(int j = init_y; j < y; j++){
	
				points.add(new Point(i,j));
				
			}
			
		}
		
		return points;
		
	}
	
	//takes a point to "a1" format
	public static String pointToString(Point p){
		char x1 = 'a';
		x1 += p.x;
		int y1 = p.y + 1;
		String s = x1 + Integer.toString(y1);
		return s;
	}
	
	public static char shiftChar(char c, int i){ //This should be static because it doesn't operate on any of the class elements
		char result = c;							//Should work if no obj
		result += i;
		return result; 
	}
}
