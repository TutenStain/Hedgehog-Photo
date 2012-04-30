package se.cth.hedgehogphoto.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelTest extends JPanel {
	private ImageIcon icon = new ImageIcon("Pictures/markers/look.png");
	private static JLabel label = new JLabel("Hejsan");
	
	public static void main(String [] args) {
		JFrame frame = new JFrame();
		JPanelTest test = new JPanelTest();
		test.setSize(400,300);
		test.setPreferredSize(new Dimension(400,300));
		test.setMaximumSize(new Dimension(400,300));
		test.setOpaque(false);
		label.setBounds(220,30,label.getWidth(), label.getHeight());
		test.add(label);
		frame.getContentPane().add(test);
		frame.getContentPane().setBackground(Color.GREEN);
		test.setBackground(Color.BLUE);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * http://www.daniweb.com/software-development/java/threads/187946/jpanel-background-picture
	 */
	 protected void paintComponent(Graphics gOrig) {
//		 gOrig.drawImage(icon.getImage(), 0,0, null);
		 super.paintComponent(gOrig);	 
		 Graphics2D g2d = (Graphics2D) gOrig;
		 g2d.drawImage(icon.getImage(), 0,0, null);
	 }

}
