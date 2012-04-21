package se.cth.hedgehogphoto.map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;

import se.cth.hedgehogphoto.LocationObject;

/**
 * Combines the map and its' markers to one pane.
 * THIS is the class to instantiate if one wants a map.
 * @author Florian
 */
public class MapWrapper extends JLayeredPane {
	
	private MapPanel map;
	private List<LocationMarker> locationMarkers;
	private List<LocationObject> locations;
	private LocationObject centerLocation;
	private final int WIDTH = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH;
	private final int HEIGHT = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT;
	
	/** Default constructor. 
	 * @param locations the locations to be shown on the map. */
	public MapWrapper(List<LocationObject> locations) {
		init(locations);
	}
	
	/** The initialization of this class. Called from constructor. */
	private void init(List<LocationObject> locations) {
		setLocations(locations);
		addMap();
		addLocationMarkers();
	}
	
	/** Returns the map, OR if it doesn't exist yet, creates it. */
	public MapPanel getMapPanel() {
		if (map == null) {
			createMapPanel();
		}
		return map;
	}
	
	/** Creates a new map, calibrates it and starts observing it. */
	private void createMapPanel() {
		map = new MapPanel();
		calibrateMap();
//		map.addObserver(this); use propertychangelistener instead.
	}
	
	/** Adds the map to the pane. */
	private void addMap() {
		add(getMapPanel(), new Integer(0), 0);
	}
	
	/** Does some basic calibrations to the map. */
	private void calibrateMap() {
		computeRecquiredZoom(); 
		map.enableOverlayPanel(false);
		map.enableControlPanel(true);
		map.enableInteraction(true);
		map.setBounds(0, 0, WIDTH, HEIGHT);
		map.setOpaque(true);
		/* TODO: Add handling for resizing of the map-window.
		   This will not be superimportant, though right now there is no 
		   "real" center if the canvas-borders change size. */
		
	}
	
	/** Creates and adds the visual representation of the locations - markers. */
	private void addLocationMarkers() {
		List<Point> locationPoints = getPixelCoordinates();
		int nbrOfLocations = locationPoints.size();
        LocationMarker [] markers = new LocationMarker[nbrOfLocations];
        for(int i = 0; i < nbrOfLocations; i++) {
        	markers[i] = new LocationMarker(locationPoints.get(i));
        	int layer = new Integer(locationPoints.get(i).y);
        	map.addObserver(markers[i]);
        	add(markers[i], layer, 1);
        }
	}
	
	/** Refreshes the view. Supposed to be used when new locations have been set. */
	private void cleanBuild() {
		removeAll();
		addMap();
		addLocationMarkers();
	}
	
	/** Tells the map to center its' view to the specified centerLocation. */
	private void centerMap() {
		Point position = computeMapPosition(centerLocation);
		map.setCenterPosition(position);
	}
	
	/** Calculates the proper zoom so that every location fits on the 
	 *  visible part of the map. */
	private void computeRecquiredZoom() {
		int zoom = 16;
		map.setZoom(zoom);
		centerMap();
		while (!allLocationsVisible() && zoom != 1) {
			map.setZoom(--zoom);
			centerMap();
		} 
	}
	
	/** Returns a list of pixel coordinates for all Locations.
	 *  These pixel coordinates specify where, relative to the map,
	 *  where the locationMarkers have to be placed. */
	private List<Point> getPixelCoordinates() {
		List<Point> pixelCoordinates = new ArrayList<Point>();
		int nbrOfLocations = locations.size();
		Point temp;
		for (int i = 0; i < nbrOfLocations; i++) {
			temp = computeMapPosition(locations.get(i));
			temp.x = temp.x - map.getMapPosition().x;
			temp.y = temp.y - map.getMapPosition().y;
			pixelCoordinates.add(temp);
		}
		System.out.println("");
		return pixelCoordinates;
	}
	
	private Point computeMapPosition(LocationObject location) {
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		return computeMapPosition(longitude, latitude);
	}
	
	private Point computeMapPosition(double longitude, double latitude) {
		Point position = map.computePosition(new Point2D.Double(longitude, latitude));
		return position;
	}
	
	/**Tells the model that there are new Locations. */
	public void setLocations(List<LocationObject> locations) {
		this.locations = locations;
		updateCenterLocation();
	}
	
	/** Internal method for updating the centerLocation-variable. */
	private void updateCenterLocation() {
		double averageLongitude = averageLongitude();
		double averageLatitude = averageLatitude();
		centerLocation = new LocationObject(averageLongitude, averageLatitude);
	}
	
	/** Returns true if all Locations are visible on the map. */
	private boolean allLocationsVisible() {
		boolean result = true;
		List<Point> list = getPixelCoordinates();
		int nbrOfLocations = list.size();
		for (int i = 0; i < nbrOfLocations; i++) {
			if (!validPixelCoordinate(list.get(i))) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	/** Returns true if the passed pixel p is: 0 < p < SIZE.
	 *  ie if it is part of the map. Returns false otherwise. */
	private boolean validPixelCoordinate(Point pixelCoordinate) {
		boolean longitudeOK = (pixelCoordinate.x > 0 && pixelCoordinate.x < WIDTH);
		boolean latitudeOK = (pixelCoordinate.y > 0 && pixelCoordinate.y < HEIGHT);
		return (longitudeOK && latitudeOK);
	}
	
	/** Returns the average Latitude for the stored locations.
	 *  If there are no locations, 0.0 is returned. */
	private double averageLatitude() {
		double totalLatitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLatitude += locations.get(i).getLatitude();
		}
		
		double averageLatitude = nbrOfLocations != 0 ? totalLatitude / nbrOfLocations : 0.0; 
		return averageLatitude;
	}
	
	/** Returns the average Longitude for the stored locations.
	 *  If there are no locations, 0.0 is returned. */
	private double averageLongitude() {
		double totalLongitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLongitude += locations.get(i).getLongitude();
		}

		double averageLongitude = nbrOfLocations != 0 ? totalLongitude / nbrOfLocations : 0.0; 
		return averageLongitude;
	}
}
