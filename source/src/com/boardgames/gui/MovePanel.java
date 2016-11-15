package com.boardgames.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

public class MovePanel extends JPanel{

	private static final long serialVersionUID = -7315587432222951320L;

	private static final Dimension Dim = new Dimension(200,100);

	private JScrollPane scrollPane;
	
	JList<String> data;
	DefaultListModel<String> listModel;
	
	public MovePanel(){
		super(new BorderLayout());
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		listModel = new DefaultListModel<>
		();
		
		data = new JList<>(listModel);
		data.setPreferredSize(Dim);
		
		this.scrollPane = new JScrollPane(data);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(Dim);
		this.add(scrollPane, BorderLayout.CENTER);
		//this.add(data, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void addMove(String s){
		
		listModel.addElement(s);
		revalidate();
		repaint();
	}
	
}
