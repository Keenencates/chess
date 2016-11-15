package com.boardgames.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.boardgames.engine.AbstractGameEngine;
import com.boardgames.engine.CheckersGame;
import com.boardgames.engine.ChessGame;
import com.boardgames.engine.HexGame;

public class BoardGameWindow {

	AbstractGameEngine boardGame;
	public JFrame frame;
	public JPanel gamePanel;
	public MovePanel movePanel;
	
	public BoardGameWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Board Games");
		frame.setLayout(new BorderLayout());
		frame.setBounds(100, 100, 900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JMenuBar menuBar = new JMenuBar();
		initMenuBar(menuBar);
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(true);
	}
	
	private void initMenuBar(JMenuBar menuBar){
		menuBar.add(createFileMenu());
	}
	
	private void update(){
		frame.revalidate();
		frame.repaint();
	}
	
	private void initGamePanel(){
		frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
		update();
	}
	
	private void clearGamePanel(){
		if(gamePanel != null){
				frame.getContentPane().remove(gamePanel);
				update();
		}
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenu newGame = new JMenu("New Game");
		
		final JMenuItem newCheckersGame = new JMenuItem("Checkers");
		newCheckersGame.addActionListener(e ->{
			boardGame = new CheckersGame();
			clearGamePanel();
			initMovePanel();
			gamePanel = new SquareBoardPanel(boardGame.getMap(), boardGame, movePanel);
			initGamePanel();
			});
		
		final JMenuItem newChessGame = new JMenuItem("Chess");
		newChessGame.addActionListener(e ->{
			boardGame = new ChessGame();
			clearGamePanel();
			initMovePanel();
			gamePanel = new SquareBoardPanel(boardGame.getMap(), boardGame, movePanel);
			initGamePanel();
			});
		
		final JMenuItem newHexGame = new JMenuItem("Hex");
		newHexGame.addActionListener(e ->{
			boardGame = new HexGame();
			clearGamePanel();
			gamePanel = new HexPanel();
			initGamePanel();
			});
		
		fileMenu.add(newGame);
		newGame.add(newCheckersGame);
		newGame.add(newChessGame);
		newGame.add(newHexGame);
		
		return fileMenu;
	}

	private void initMovePanel() {
		movePanel = new MovePanel();
		frame.getContentPane().add(movePanel, BorderLayout.EAST);
		update();
		
	}
}
