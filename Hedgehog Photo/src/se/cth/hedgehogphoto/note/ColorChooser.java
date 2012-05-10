package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorChooser extends JPanel{
	
	private Dimension side;
	private JButton[] grid = new JButton[9];
	
	public ColorChooser(){
		this.side = side;
		this.setLayout(new GridLayout(3, 3));
		this.arrangeButtons();
	}
	
	public void arrangeButtons(){
		for(int i=0;i<grid.length;i++){
			grid[i] = new JButton();
			this.add(grid[i]);
		}
		grid[0].setBackground(Color.white);
		grid[1].setBackground(Color.black);
		grid[2].setBackground(Color.gray);
		grid[3].setBackground(Color.blue);
		grid[4].setBackground(Color.green);
		grid[5].setBackground(Color.cyan);
		grid[6].setBackground(Color.red);
		grid[7].setBackground(Color.orange);
		grid[8].setBackground(Color.yellow);
	}
	
	public void addListener(ActionListener l){
		for(int i=0;i<grid.length;i++){
			grid[i].addActionListener(l);
		}
	}
}
