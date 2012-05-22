package se.cth.hedgehogphoto.geocoding;

import se.cth.hedgehogphoto.geocoding.controller.GeocodingController;
import se.cth.hedgehogphoto.geocoding.view.GeoSearchPanel;
import se.cth.hedgehogphoto.view.PhotoPanel;

/**
 * Initiates the geocoding-subsystem.
 * @author Florian Minges
 */
public class GeocodingInitiator {
	
	public static void main(String [] args) {
		new GeocodingInitiator("paris", null);
	}
	
	public GeocodingInitiator() {
		this(null, null);
	}
	
	public GeocodingInitiator(String initialSearch, PhotoPanel panel) {
		GeoSearchPanel view = GeoSearchPanel.getInstance();
		GeocodingController controller = GeocodingController.getInstance(view);
		view.setInitialSearchBoxText(initialSearch);
		controller.setPhotoPanel(panel);
	}
	
	public GeocodingInitiator(String initialSearch) {
		GeoSearchPanel view = GeoSearchPanel.getInstance();
		GeocodingController controller = GeocodingController.getInstance(view);
		view.setInitialSearchBoxText(initialSearch);
	}
}
