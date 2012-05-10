package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class NoteDrawPanel extends JPanel{
	
	private static int circleDiam = 10;
	private static Color color = Color.black;
	
	public NoteDrawPanel(int width, int height){

		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(width, height));
		
		this.addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent me) {
				NoteDrawPanel board = (NoteDrawPanel)me.getSource();
				PaintUtils.paintCircle(board.getGraphics(), me.getX(), me.getY(), circleDiam, color);
			}
		});

		this.addMouseMotionListener(new MouseAdapter() { 
			public void mouseDragged(MouseEvent me){
				NoteDrawPanel board = (NoteDrawPanel)me.getSource();
				PaintUtils.paintCircle(board.getGraphics(), me.getX(), me.getY(), circleDiam, color);
			}
		});
	}//constructor

	public void setCircleDiam(int diam){
		circleDiam = diam;
	}
	public void setColor(Color c){
		color = c;
	}
	
}
