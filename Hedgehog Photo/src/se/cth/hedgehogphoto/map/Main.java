package se.cth.hedgehogphoto.map;

import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.Location;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Location loc1 = new Location(40.2,38.442);
		Location loc2 = new Location(33,35.442);
		Location loc3 = new Location(40.2,-3.442);
		Location loc4 = new Location(-40.2,-38.442);
		List<Location> locations = new ArrayList<Location>();
		locations.add(loc1);
		locations.add(loc2);
		locations.add(loc3);
		locations.add(loc4);
		
		PixelFinder pixelFinder = new PixelFinder();
		String sträng = pixelFinder.getPixelCoordinates(locations);
		System.out.println(sträng);
	}

}
