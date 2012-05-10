package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NotePreview extends JPanel{
	
	public NotePreview(int side){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(side, side));
	}
	
	public void paintPreview(int diam, Color c){
        PaintUtils.erasePainting(this);
		PaintUtils.paintCircle(this.getGraphics(), this.getWidth()/2, this.getHeight()/2, diam, c);
	}
}
