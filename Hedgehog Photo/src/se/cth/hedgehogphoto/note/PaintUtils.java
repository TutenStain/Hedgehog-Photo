package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author David Grankvist
 */

public class PaintUtils {
	
	public static void paintOval(Graphics graphics, int x, int y, int w, int h, Color color){
		graphics.setColor(color);
		graphics.fillOval(x, y, w, h);
		graphics.setColor(color);
		graphics.drawOval(x, y, w, h);
	}
	
	public static void paintCircle(Graphics graphics, int x, int y, int diameter, Color color){
		paintOval(graphics, x, y, diameter, diameter, color);
	}
	
	public static void paintRect(Graphics graphics, int x, int y, int w, int h, Color color){
		graphics.setColor(color);
		graphics.fillRect(x, y, w, h);
		graphics.setColor(color);
		graphics.drawRect(x, y, w, h);
	}
	
	public static void erasePainting(JPanel panel){
		paintRect(panel.getGraphics(), 0, 0, panel.getWidth(), 
					panel.getHeight(), panel.getBackground());
	}
}
