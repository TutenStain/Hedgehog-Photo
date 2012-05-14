package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author David
 */

public class PaintUtils {
	
	public static void paintOval(Graphics g, int x, int y, int w, int h, Color c){
		g.setColor(c);
		g.fillOval(x, y, w, h);
		g.setColor(c);
		g.drawOval(x, y, w, h);
	}
	
	public static void paintCircle(Graphics g, int x, int y, int diam, Color c){
		paintOval(g, x, y, diam, diam, c);
	}
	
	public static void paintRect(Graphics g, int x, int y, int w, int h, Color c){
		g.setColor(c);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		g.drawRect(x, y, w, h);
	}
	/*Graphically resets a white JPanel*/
	public static void erasePainting(JPanel panel){
		paintRect(panel.getGraphics(), 0, 0, panel.getWidth(), panel.getHeight(), Color.white);
	}
}
