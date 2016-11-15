package com.boardgames.engine.utils;
import java.util.HashSet;

public interface Observable{

	HashSet<Observer> observerList = new HashSet<>();
	
	public default void addObserver(Observer o){
		observerList.add(o);
	}
	
	public default void deleteObserver(Observer o){
		observerList.remove(o);
	}
	
	public default void notifyObservers(){
		for(Observer each: observerList){
			each.update();
		}
	}	
	
}
