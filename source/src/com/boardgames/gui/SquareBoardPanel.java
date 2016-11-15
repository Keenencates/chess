package com.boardgames.gui;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JPanel;

import com.boardgames.engine.AbstractGameEngine;
import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.map.GameCell;
import com.boardgames.engine.piece.AbstractGamePiece;

public class SquareBoardPanel extends JPanel {

	private static final long serialVersionUID = 1762711953744904767L;
	HashMap<Point, CellPanel> boardCells;
	
	public GameCell source;
	public GameCell dest;
	public AbstractGamePiece pieceM;
	public AbstractGameEngine engine;
	
	public static int boardSize = 8;
	
	private static Dimension boardDim = new Dimension(800, 800);
	
	public SquareBoardPanel(AbstractGameMap map, AbstractGameEngine engine, MovePanel movePanel){
		super(new GridLayout(8,8));
		boardCells = new HashMap<>();
		for(int i = 0; i <boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				Point p = new Point(j, 7-i);
				CellPanel tempCell = new CellPanel(this, p, map, engine, movePanel);
				boardCells.put(p, tempCell);
				this.add(tempCell);
			}
		}
		setPreferredSize(boardDim);
		validate();
	}

	public void drawBoard(AbstractGameMap board) {
		removeAll();
		for(int i = 0; i <boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				Point p = new Point(j, 7-i);
				boardCells.get(p).drawCell(board);
				add(boardCells.get(p));
			}
		}
		
		revalidate();
		repaint();
	}

}
