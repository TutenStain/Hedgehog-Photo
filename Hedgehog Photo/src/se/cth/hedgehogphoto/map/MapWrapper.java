package se.cth.hedgehogphoto.map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import se.cth.hedgehogphoto.LocationObject;

public class MapWrapper extends JLayeredPane implements Observer {
	
	private MapPanel map;
	private List<LocationObject> locations;
	private LocationObject centerLocation;
	private final int WIDTH = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH;
	private final int HEIGHT = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT;
	
	public MapWrapper(List<LocationObject> locations) {
		init(locations);
	}
	
	public void init(List<LocationObject> locations) {
		setLocations(locations);
		addMap();
		addLocationMarkers();
	}
	
	public MapPanel getMapPanel() {
		if (map == null) {
			createMapPanel();
		}
		return map;
	}
	
	private void createMapPanel() {
		map = new MapPanel();
		calibrateMap();
		map.addObserver(this);
	}
	
	public void addMap() {
		add(getMapPanel(), new Integer(0), 0);
	}
	
	public void calibrateMap() {
		computeRecquiredZoom(); 
		map.enableOverlayPanel(false);
		map.enableControlPanel(true);
		map.enableInteraction(true);
		map.setBounds(0, 0, WIDTH, HEIGHT);
		map.setOpaque(true);
		//TODO: Add handling for resizing of the map-window.
	}
	
	private void addLocationMarkers() {
		List<Point> locationPoints = getPixelCoordinates();
		int nbrOfLocations = locationPoints.size();
        JLabel [] markers = new JLabel[nbrOfLocations];
        for(int i = 0; i < nbrOfLocations; i++) {
        	markers[i] = new LocationMarker(locationPoints.get(i));
        	int layer = new Integer(locationPoints.get(i).y);
        	add(markers[i], layer, 1);
        }
	}
	
	public void cleanBuild() {
		removeAll();
		addMap();
		addLocationMarkers();
	}
	
	private void centerMap() {
		Point position = computeMapPosition(centerLocation);
		map.setCenterPosition(position);
	}
	
	private void computeRecquiredZoom() {
		int zoom = 16;
		map.setZoom(zoom);
		centerMap();
		while (!allLocationsVisible() && zoom != 1) {
			map.setZoom(--zoom);
			centerMap();
		} 
	}
	
	public List<Point> getPixelCoordinates() {
		List<Point> pixelCoordinates = new ArrayList<Point>();
		int nbrOfLocations = locations.size();
		Point temp;
		for (int i = 0; i < nbrOfLocations; i++) {
			temp = computeMapPosition(locations.get(i));
			temp.x = temp.x - map.getMapPosition().x;
			temp.y = temp.y - map.getMapPosition().y;
//			System.out.println(locations.get(i).toString());
//			System.out.println(temp.x + ", " + temp.y);
			double lon = map.position2lon(map.getMapPosition().x);
			double lat = map.position2lat(map.getMapPosition().y);
//			System.out.println(lon + "," + lat + "  vs  " + locations.get(i).getLongitude() + "," + locations.get(i).getLatitude());
			pixelCoordinates.add(temp);
		}
		System.out.println("");
		return pixelCoordinates;
	}
	
	public Point computeMapPosition(LocationObject location) {
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		return computeMapPosition(longitude, latitude);
	}
	
	public Point computeMapPosition(double longitude, double latitude) {
		Point position = map.computePosition(new Point2D.Double(longitude, latitude));
		return position;
	}
	
	public void setLocations(List<LocationObject> locations) {
		this.locations = locations;
		updateCenterLocation();
	}
	
	public void updateCenterLocation() {
		double averageLongitude = averageLongitude();
		double averageLatitude = averageLatitude();
		centerLocation = new LocationObject(averageLongitude, averageLatitude);
	}
	
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
	
//	@Deprecated
//	private boolean allLocationsVisible2() {
//		boolean result = true;
//		int nbrOfLocations = locations.size();
//		for (int i = 0; i < nbrOfLocations; i++) {
//			if (!withinBounds(locations.get(i))) {
//				result = false;
//				break;
//			}
//		}
//		
//		return result;
//	}
	
	private boolean validPixelCoordinate(Point pixelCoordinate) {
		boolean longitudeOK = (pixelCoordinate.x > 0 && pixelCoordinate.x < WIDTH);
		boolean latitudeOK = (pixelCoordinate.y > 0 && pixelCoordinate.y < HEIGHT);
		return (longitudeOK && latitudeOK);
	}
	
//		/** Won't use this. */
//	@Deprecated
//	private boolean withinBounds(LocationObject location) {
//		double longitude = location.getLongitude();
//		double latitude = location.getLatitude();
//		Point topLeft = map.getMapPosition();
//		double topLeftLongitude = map.position2lon(topLeft.x);
//		double topLeftLatitude = map.position2lat(topLeft.y);
//		Point bottomRight = new Point(topLeft.x + WIDTH, topLeft.y + HEIGHT);
//		double bottomRightLongitude = map.position2lon(bottomRight.x);
//		double bottomRightLatitude = map.position2lat(bottomRight.y);
//		
//		boolean longitudeWithinBounds = (longitude > topLeftLongitude && longitude < bottomRightLongitude);
//		boolean latitudeWithinBounds = (latitude > topLeftLatitude && latitude < bottomRightLatitude);
//		
//		return (longitudeWithinBounds && latitudeWithinBounds);
//	}
	
	private LocationObject getCenterLocation() {
		return centerLocation.clone();
	}
	
	private double averageLatitude() {
		double totalLatitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLatitude += locations.get(i).getLatitude();
		}
		
		/** IF POSSIBLE: Doesn't currently handle an empty list. */
		double averageLatitude = totalLatitude / nbrOfLocations; 
		return averageLatitude;
	}
	
	private double averageLongitude() {
		double totalLongitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLongitude += locations.get(i).getLongitude();
		}
		
		/** IF POSSIBLE: Doesn't currently handle an empty list. */
		double averageLongitude = totalLongitude / locations.size(); 
		return averageLongitude;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		cleanBuild();
	}

}
