package se.cth.hedgehogphoto.map.geocoding;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.map.controller.MapController;
import se.cth.hedgehogphoto.map.geocoding.controller.GeocodingController;
import se.cth.hedgehogphoto.map.geocoding.view.GeoSearchPanel;
import se.cth.hedgehogphoto.map.model.MapModel;
import se.cth.hedgehogphoto.map.view.MapView;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;

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
		GeocodingController controller = new GeocodingController(view);
		
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view);
		frame.pack();
		frame.getContentPane().setVisible(true);
		frame.setVisible(true);
		
	}
}
