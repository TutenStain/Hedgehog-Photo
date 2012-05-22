package se.cth.hedgehogphoto.note.view;

import java.awt.Color;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.note.PaintUtils;

/**
 * 
 * @author David Grankvist
 */

@SuppressWarnings("serial")
public class NotePreview extends JPanel{
	
	public NotePreview(){
		this.setBackground(Color.white);
	}
	
	public void paintPreview(int diameter, Color color){
        PaintUtils.erasePainting(this);
		PaintUtils.paintCircle(this.getGraphics(), this.getWidth()/2, 
								this.getHeight()/2, diameter, color);
	}
}
