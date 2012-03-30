package se.cth.hedgehogphoto.map;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.Location;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Location loc1 = new Location(33,35.442); // 	(165,73)		pixel coordinates in a 256x256-map
		Location loc2 = new Location(5.039,42.13); // 	(184,159)
		Location loc3 = new Location(10,10); // 		(92,145)
		Location loc4 = new Location(16.55,2.99); // 	(73,125)
		
//		Location loc1 = new Location(24.1,10); 
//		Location loc2 = new Location(3.37,34); 
//		Location loc3 = new Location(33.111,52.13); 
//		Location loc4 = new Location(40,12.55);
		List<Location> locations = new ArrayList<Location>();
		locations.add(loc1);
		locations.add(loc2);
		locations.add(loc3);
		locations.add(loc4);
		
		PixelFinder pixelFinder = new PixelFinder();
		MapPath mapPath = new MapPath();
		mapPath.setMapSize(new Dimension(256,256));
		mapPath.setMarkers(locations);
		String mapURL = mapPath.getPath();
		String string = pixelFinder.getPixelCoordinates(locations);
		System.out.println(string);
		System.out.println(mapURL + "\n\n");
		
		CopyOfPixelFinder copyOfPixelFinder = new CopyOfPixelFinder();
		String newString = copyOfPixelFinder.getPixelCoordinates(locations);
		String centerlonglat = copyOfPixelFinder.getMapCenterInLongLat(locations);
		Location loc = new Location(centerlonglat);
//		loc.setLocation(copyOfPixelFinder.mapCenterLatitude(locations), copyOfPixelFinder.mapCenterLongitude(locations));
		mapPath.setCenter(loc);
		mapURL = mapPath.getPath();
		
		System.out.println(newString);
		System.out.println(mapURL);
		
	}

}
