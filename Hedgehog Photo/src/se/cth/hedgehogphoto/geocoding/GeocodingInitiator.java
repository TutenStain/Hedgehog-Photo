package se.cth.hedgehogphoto.map.geocoding;

import java.awt.Dimension;

import javax.swing.JFrame;

import se.cth.hedgehogphoto.map.geocoding.controller.GeocodingController;
import se.cth.hedgehogphoto.map.geocoding.view.GeoSearchPanel;

/**
 * Initiates the geocoding-subsystem.
 * @author Florian Minges
 */
public class GeocodingInitiator {
	
	public static void main(String [] args) {
		new GeocodingInitiator();
	}
	
	public GeocodingInitiator() {
		GeoSearchPanel view = new GeoSearchPanel();
		new GeocodingController(view);
		
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view);
		frame.pack();
		frame.getContentPane().setVisible(true);
		frame.setVisible(true);
		
	}
}
