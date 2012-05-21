package se.cth.hedgehogphoto.geocoding;

import java.awt.Dimension;

import javax.swing.JFrame;

import se.cth.hedgehogphoto.geocoding.controller.GeocodingController;
import se.cth.hedgehogphoto.geocoding.view.GeoSearchPanel;

/**
 * Initiates the geocoding-subsystem.
 * @author Florian Minges
 */
public class GeocodingInitiator {
	
	public static void main(String [] args) {
		new GeocodingInitiator("paris");
	}
	
	public GeocodingInitiator() {
		this(null);
	}
	
	public GeocodingInitiator(String initialSearch) {
		GeoSearchPanel view = new GeoSearchPanel();
		new GeocodingController(view);
		view.setInitialSearchBoxText(initialSearch);
		
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view);
		frame.pack();
		frame.getContentPane().setVisible(true);
		frame.setVisible(true);
		
	}
}
