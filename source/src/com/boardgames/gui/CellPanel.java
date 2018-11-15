package com.boardgames.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.boardgames.engine.ChessGame;
import com.boardgames.engine.map.AbstractGameMap;
import com.boardgames.engine.utils.EngineUtils;

public class CellPanel extends JPanel{

	private static final long serialVersionUID = -5058846076542685149L;
	private static Dimension tileDim = new Dimension(100, 100);
	private Point CellLocation;
	
	CellPanel(SquareBoardPanel boardPanel, Point CellLocation, AbstractGameMap board, ChessGame engine, MovePanel movePanel){
		super(new GridBagLayout());
		this.CellLocation = CellLocation;
		setPreferredSize(tileDim);
		assignColor();
		assignIcon(board);
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!false) {
					int firstRank;
					
					if(engine.getCurrentPlayer().getName().equals("White Player")){
						firstRank = 0;
					}else{
						firstRank = 7;
					}
					
					if(SwingUtilities.isRightMouseButton(e)){
						
						boardPanel.source = null;
						boardPanel.dest = null;
						for(CellPanel each: boardPanel.boardCells.values()){
							each.assignColor();
						}
						
					}else if(SwingUtilities.isLeftMouseButton(e)){
						
						engine.getCurrentPlayer().generateMoves();
						engine.createMoves();
						engine.findForceMoves();
						engine.createMoves();
						engine.filterMoves();
						engine.checkWinner();
						if(engine.isWinner()) {
							movePanel.addMove("Game has ended in a stalemate!");
						}
						
						
						if(boardPanel.source == null){
							boardPanel.source = board.getCell(CellLocation);
							boardPanel.pieceM = board.getPiece(CellLocation);
							if(boardPanel.pieceM == null){
								boardPanel.source = null;
							}
							if(engine.getCurrentPlayer().getPieces().contains(boardPanel.pieceM)){
								setBackground(Color.GREEN);
							}
							
							
							for(String each: engine.getCurrentPlayer().getCurrentMoves().keySet()){
								
								if(!each.equals("0-0") && !each.equals("0-0-0") && each.charAt(2) != 'x'){
									Vector<Point> temp = EngineUtils.parseSquareInput(each);
									if(temp.elementAt(0).equals(CellLocation)){
										boardPanel.boardCells.get(temp.elementAt(1)).setBackground(Color.RED);
									}
								}else{
									if(each.equals("0-0")){
										if(new Point(4, firstRank).equals(CellLocation)){
											boardPanel.boardCells.get(new Point(6, firstRank)).setBackground(Color.RED);
										}
											
									}else if(each.equals("0-0-0")){
										if(new Point(4, firstRank).equals(CellLocation)){
											boardPanel.boardCells.get(new Point(2, firstRank)).setBackground(Color.RED);
										}
									}else if(each.charAt(2) == 'x'){
										String enPassant = each.replace('x', '-');
										Vector<Point> temp = EngineUtils.parseSquareInput(enPassant);
										if(temp.elementAt(0).equals(CellLocation)){
											boardPanel.boardCells.get(temp.elementAt(1)).setBackground(Color.RED);
										}
									}
								}
							}
						}else{
							boardPanel.dest = board.getCell(CellLocation);
							String turn = EngineUtils.pointToString(boardPanel.source.getPoint()) + 
									"-" + EngineUtils.pointToString(boardPanel.dest.getPoint());
							
							if((turn.equals("e1-g1")  || turn.equals("e8-g8"))
									&& engine.getCurrentPlayer().getCurrentMoves().containsKey("0-0")){
								turn = "0-0";
							}
							
							if((turn.equals("e1-c1")  || turn.equals("e8-c8"))
									&& engine.getCurrentPlayer().getCurrentMoves().containsKey("0-0-0")){
								turn = "0-0-0";
							}
							
							if(engine.getCurrentPlayer().getCurrentMoves().containsKey(turn.replace('-', 'x'))){
								turn = turn.replace('-', 'x');
							}
							
							
							if(engine.getCurrentPlayer().getCurrentMoves().containsKey(turn)){
								engine.getCurrentPlayer().getMoveFactory().executeCommand(turn);
								engine.getCurrentPlayer().checkPromotions();
								//if(engine.getCurrentPlayer().getCurrentMoves().isEmpty()){
								//	engine.swapPlayer();
								//}
								engine.swapPlayer();
								engine.getCurrentPlayer().generateMoves();
								engine.createMoves();
								engine.findForceMoves();
								engine.createMoves();
								engine.checkWinner();
								
								movePanel.addMove(turn);
								
								Boolean check = engine.checkCheck();
								if(check){
									movePanel.addMove(engine.getCurrentPlayer().getName() + " in check!");
								}
								if(engine.isWinner()){
									movePanel.addMove("Game has ended!");
									if(check){
										movePanel.addMove(engine.getOtherPlayer().getName() + " has won!");
									}else{
										movePanel.addMove("Game has ended in a stalemate!");
									}
								}
								
								boardPanel.source = null;
								boardPanel.dest = null;
								boardPanel.source = null;
							}else{
								boardPanel.source = null;
								boardPanel.dest = null;
								boardPanel.source = null;
							}
							for(CellPanel each: boardPanel.boardCells.values()){
								each.assignColor();
							}
							SwingUtilities.invokeLater(new Runnable(){
								@Override
								public void run(){
									boardPanel.drawBoard(board);
								}
							});
						}
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
			
		});
		validate();
	}
	
	public void assignIcon(AbstractGameMap board){
		if(!board.getCell(CellLocation).isEmpty()){
			String IconPath = "";
			try{
				
				String allegiance;
				if(board.getPiece(CellLocation).movesNorth()){
					allegiance = "w";
				}else{
					allegiance = "b";
				}
				
				String type = Character.toString(board.getPiece(CellLocation).getSymbol());
				
				IconPath = "Graphics/ChessPieces/" + allegiance + type + ".png";
				BufferedImage image = ImageIO.read(new File(IconPath));
				add(new JLabel(new ImageIcon(image)));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void assignColor() {
		if(CellLocation.y == 0 || CellLocation.y == 2 || CellLocation.y == 4 || CellLocation.y == 6){
			setBackground(CellLocation.x % 2 != 0 ? Color.WHITE : Color.DARK_GRAY);
		}else if(CellLocation.y == 1 || CellLocation.y == 3 || CellLocation.y == 5 || CellLocation.y == 7){
			setBackground(CellLocation.x % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
		}
		
	}

	public void drawCell(AbstractGameMap board) {
		removeAll();
		assignColor();
		assignIcon(board);
		validate();
		repaint();
	}

}
