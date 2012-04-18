package se.cth.hedgehogphoto.map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.Location;

/**
 * 
 * @author Florian Minges
 */
public class Main {
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 700; //Be sure to change variables in MapPanel too
	
	public static void main(String [] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.add(getMapPanel());
		frame.add(panel);
		frame.setSize(600,800);
		frame.setVisible(true);
	}
	
	public static JPanel getMapPanel() {
		MapPanel mapPanel = new MapPanel();
		List<Location> locations = new ArrayList<Location>();
		locations.add(new Location(57.0,10.0));
		locations.add(new Location(56.0,11.0));
		locations.add(new Location(55.0,12.0));
		Location center = getCenterLocation(locations);
		
		Point position = computePosition(mapPanel, center);
		mapPanel.setCenterPosition(position); 
		
		/* Sets the proper zoom, so that every location fits on the map*/
		adjustZoom(mapPanel, locations); 
		
		/* DEPRECATED COMMENT ON HOW TO DO*/
		//--------------------------------------------
		
		//HOW: 1) calculate difference in long/lat between center
//		and the outermost location (greatestDifference in both long and lat).
//		2) Convert this into the maps positional system  via computePosition(Point)
//			The outermost point shall be in the top-left corner (IMPORTANT)
//		3) Check if current zoom allows both center and outermost location
//		to be seen. (ie check if outermost location is greater than the mapposition)
//		(mapposition = top-left corner) and smaller than the center in both long&lat
//			a) if YES, increment zoom and try again
//				a1) if NO now, back one step and use that zoom
//			b) if NO, decrease zoom and try again
//				b1) if YES now, use that zoom
//		*Low longitude, high latitude
		
		//-------------------------------------------
		
		/* IF POSSIBLE: enable/disable the overlay panels in case of need*/
		mapPanel.enableAllOverlayPanels(true);
		mapPanel.enableInteraction(true);
		
		/* TODO: list is not even used... refactor!! */
		List<Point> list = getCoordinatesList(mapPanel, locations);
		mapPanel.setBounds(0, 0, WIDTH, HEIGHT);
		mapPanel.setOpaque(true);
		return mapPanel;
	}
	
	/* This method will not be used*/
	@Deprecated
	private static JPanel getJPanel() {
		JPanel panel = new JPanel();
		try {
		    // Create a URL for the image's location
		    URL url = new URL("http://maps.googleapis.com/maps/api/staticmap?size=500x400&sensor=false&format=jpg&markers=Sweden");
		    BufferedImage myPicture = ImageIO.read(url);
			JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
			panel.add( picLabel );

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		
		return panel;
	}
	
	/* FIXME: Refactor all the methods! Add new classes! 
	 * Put away the functionality to the mapPanel! */
	@Deprecated
	private static boolean coordinatesVisible(MapPanel map, List<Location> locations) {
		List<Point> list = getCoordinatesList(map, locations);
		boolean ok = true;
		int nbrOfCoordinates = list.size();
		for(int i = 0; i < nbrOfCoordinates; i++) {
			if (Math.abs(list.get(i).x) > WIDTH / 2)
				ok = false;
			else if (Math.abs(list.get(i).y) > HEIGHT / 2)
				ok = false;
			
			
		}
		return ok;
	}
	
	/* TODO: wtf does this method do? Better names please! */
	private static void evaluateCoordinates(MapPanel map, List<Location> locations) {
		List<Point> list = getCoordinatesList(map, locations);
		//print out the "real" pixels
		for (int j = 0; j<list.size(); j++)
			System.out.println("x: " + (WIDTH/2.0 + list.get(j).x) + "\t\ty: " + (HEIGHT/2.0 + list.get(j).y));
			
	}
	
	private static List<Point> getCoordinatesList(MapPanel map, List<Location> locations) {
		List<Point> coordinates = new ArrayList<Point>();
		Point temp;
		for(int i = 0; i<locations.size(); i++) {
			temp = computePosition(map, locations.get(i));
			temp.x = temp.x - map.getMapPosition().x;
			temp.y = temp.y - map.getMapPosition().y;
			coordinates.add(temp);
		}
		return coordinates;
	}
	
	private static Point computePosition(MapPanel map, Location location) {
		return map.computePosition(new Point2D.Double(location.getLatitude(), location.getLongitude()));
	}
	
	private static void adjustZoom(MapPanel map, List<Location> locations) {
		int zoom = 18;
		map.setZoom(zoom);
		Location center = getCenterLocation(locations);
		Point position = computePosition(map, center);
		map.setCenterPosition(position);
//		while (coordinatesVisible(map, locations) && zoom != 18) {
		/* IF POSSIBLE: Stick to the one below, but check that it is correct.*/
		while (!allLocationsVisible(map, locations) && zoom != 1) {
			map.setZoom(--zoom);
			position = computePosition(map, center);
			map.setCenterPosition(position);
		} 
		/* IF POSSIBLE: Delete the lines below if the adjustment zoom starts on 18.
		 * Note though that they are neccessary if it starts on 1. */
//		map.setZoom(--zoom);
//		position = map.computePosition(new Point2D.Double(center.getLatitude(), center.getLongitude()));
//		map.setCenterPosition(position);
		
		/* IF POSSIBLE: Change the name of dat function! */
		evaluateCoordinates(map,locations);
	}
	
	private static boolean allLocationsVisible(MapPanel map, List<Location> locations) {
		double outermostPointLongitude = averageLongitude(locations) + greatestLongitudeDiff(locations);
		double outermostPointLatitude = averageLatitude(locations) - greatestLatitudeDiff(locations);
		/* IF POSSIBLE: Create a local method called computePosition(double, double) */
		Point position = map.computePosition(new Point2D.Double(outermostPointLatitude, outermostPointLongitude));

		return withinBounds(position, map);
	}
	
	private static boolean withinBounds(Point position, MapPanel map) {
		Point corner = map.getMapPosition();
		Point center = map.getCenterPosition();
		
		/* IF POSSIBLE: Make it more readable, maybe add some help-functions. Readability! */
		boolean greaterThanCorner = (corner.getX() - position.getX() < WIDTH / 2 && corner.getY() - position.getY() < HEIGHT / 2);
		boolean smallerThanCenter = (position.getX() < center.getX() && position.getY() < center.getY());
		return (greaterThanCorner && smallerThanCenter);
	}
	
	private static Location getCenterLocation(List<Location> locations) {
		double lon = averageLongitude(locations);
		double lat = averageLatitude(locations);
		return new Location(lon, lat);
	}
	
	/**
	 * Returns the biggest difference in latitude plus a margin 
	 * between the center and a the farthest location away (seen in latitude). 
	 */
	private static double greatestLatitudeDiff(List<Location> locations) {
		Location center = getCenterLocation(locations);
		double latitudeDiff = Math.abs(center.getLatitude() - locations.get(0).getLatitude());
		double temp;
		double nbrOfLocations = locations.size();
		
		for(int i = 1; i < nbrOfLocations; i++) {
			temp = Math.abs(center.getLatitude() - locations.get(i).getLatitude());
			if (latitudeDiff < temp) {
				latitudeDiff = temp;
			}
		}
		return addMargin(latitudeDiff); 
	}
	
	private static double greatestLongitudeDiff(List<Location> locations) {
		Location center = getCenterLocation(locations);
		double longitudeDiff = Math.abs(center.getLongitude() - locations.get(0).getLongitude());
		double temp;
		double nbrOfLocations = locations.size();
		
		for(int i = 1; i < nbrOfLocations; i++) {
			temp = Math.abs(center.getLongitude() - locations.get(i).getLongitude());
			if (longitudeDiff < temp) {
				longitudeDiff = temp;
			}
		}
		return addMargin(longitudeDiff); 
	}
	
	/**
	 * Returns the distance with a margin.
	 * This is to avoid having a point on the border, ie not able to place a marker above.
	 */
	private static double addMargin(double distance) {
		return distance * 1.2; 
	}
	
	private static double averageLatitude(List<Location> locations) {
		double totalLatitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLatitude += locations.get(i).getLatitude();
		}
		
		/** IF POSSIBLE: Doesn't currently handle an empty list. */
		double averageLatitude = totalLatitude / nbrOfLocations; 
		return averageLatitude;
	}
	
	private static double averageLongitude(List<Location> locations) {
		double totalLongitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLongitude += locations.get(i).getLongitude();
		}
		
		/** IF POSSIBLE: Doesn't currently handle an empty list. */
		double averageLongitude = totalLongitude / locations.size(); 
		return averageLongitude;
	}
	
	//-------------------------------------------------------------------------------
	// CURRENTLY UNUSED METHODS, 	which may be useful at a later stage. 
	
	@Deprecated
	private static double getLocationsInfo(String maxMin, String latLong, List<Location> locations) {
		String wantedInfo = maxMin + latLong;
		switch(wantedInfo) {
			case "maxLatitude": return getLocationsMaxLatitude(locations);
			case "minLatitude": return getLocationsMinLatitude(locations); 	
			case "maxLongitude": return getLocationsMaxLongitude(locations); 	
			case "minLongitude": return getLocationsMinLongitude(locations);
			default: return 0.0; //better to return some kind of error-code? (illegal long/lat?)
		}
	}
	
	@Deprecated
	private static double getLocationsMaxLatitude(List<Location> locations) {
		double maxLatitude = locations.get(0).getLatitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLatitude() > maxLatitude) {
				maxLatitude = location.getLatitude();
			}
		}
		return maxLatitude;
	}
	
	@Deprecated
	private static double getLocationsMinLatitude(List<Location> locations) {
		double minLatitude = locations.get(0).getLatitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLatitude() < minLatitude) {
				minLatitude = location.getLatitude();
			}
		}
		return minLatitude;
	}
	
	@Deprecated
	private static double getLocationsMaxLongitude(List<Location> locations) {
		double maxLongitude = locations.get(0).getLongitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLongitude() > maxLongitude) {
				maxLongitude = location.getLongitude();
			}
		}
		return maxLongitude;
	}
	
	@Deprecated
	private static double getLocationsMinLongitude(List<Location> locations) {
		double minLongitude = locations.get(0).getLongitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLongitude() < minLongitude) {
				minLongitude = location.getLongitude();
			}
		}
		return minLongitude;
	}
	
	//-------------------------------------------------------------------------------

}
