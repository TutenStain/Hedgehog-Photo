package se.cth.hedgehogphoto.geocoding;

import se.cth.hedgehogphoto.geocoding.controller.GeocodingController;
import se.cth.hedgehogphoto.geocoding.view.GeoSearchPanel;

/**
 * Initiates the geocoding-subsystem.
 * @author Florian Minges
 */
public class GeocodingInitiator {
	
	public static void main(String [] args) {
		new GeocodingInitiator("paris", "test.jpg");
	}
	
	public GeocodingInitiator() {
		this(null, null);
	}
	
	public GeocodingInitiator(String initialSearch, String imagePath) {
		GeoSearchPanel view = GeoSearchPanel.getInstance();
		GeocodingController controller = GeocodingController.getInstance(view);
		view.setInitialSearchBoxText(initialSearch);
		controller.setImagePath(imagePath);
		
	}
}
