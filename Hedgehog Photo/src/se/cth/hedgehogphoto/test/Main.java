package se.cth.hedgehogphoto.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	public static void main(String [] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		try {
		    // Create a URL for the image's location
		    URL url = new URL("http://maps.googleapis.com/maps/api/staticmap?size=400x400&sensor=false&format=jpg&markers=Sweden");
		    BufferedImage myPicture = ImageIO.read(url);
			JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
			panel.add( picLabel );

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		frame.add(panel);
		frame.setSize(600,600);
		frame.pack();
		frame.setVisible(true);
	}

}
