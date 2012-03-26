package se.cth.hedgehogphoto.map;

import java.util.List;

import se.cth.hedgehogphoto.Location;

/**
 * This whole class is based on the article found in the link below.
 * It is pretty much copy and paste (changed variable names etc for better readability
 * and added some other help-functions). The "main-purpose/part" of this class
 * though is from Caelen King. ALL the structure is his. 
 * http://www.articlesbase.com/ecommerce-articles/how-to-make-google-static-maps-interactive-659452.html
 * @author Caelen King
 * @modifiedBy Florian
 *
 */
public class PixelFinder {
	private final int MAP_SIZE = 256;

	private double atanh(double rad) {
		return Math.log(((1 + rad) / (1 - rad))) / 2;
	}

	private double getZoom(double span) {
		double zoom = (180.00 / span) * (MAP_SIZE / 256.00);
		zoom = Math.log(zoom)/Math.log(2.0); //logaritm of 'zoom' base 2
		return Math.floor(zoom);

	}

	public String getPixelCoordinates(List<Location> markerList) {
		//TODO Refactor this class, split up in several smaller functions; READABILITY!
		
		StringBuilder returnString = new StringBuilder("");
		/**
		 * find our centre - we can reuse some of these variable later
		 */	
		double m_maxLatitude = getLocationsInfo("max", "Latitude", markerList);
		double m_minLatitude = getLocationsInfo("min", "Latitude", markerList);
		double m_maxLongitude = getLocationsInfo("max", "Longitude", markerList);
		double m_minLongitude = getLocationsInfo("min", "Longitude", markerList);
		double atanhsinO = atanh(Math.sin(m_maxLatitude * Math.PI / 180.00));
		double atanhsinD = atanh(Math.sin(m_minLatitude * Math.PI / 180.00));
		double atanhCentre = (atanhsinD + atanhsinO) / 2;
		double radianOfCentreLatitude = Math.atan(Math.sinh(atanhCentre));
		double centreLatitude = radianOfCentreLatitude * 180.00 / Math.PI; //turn it to degrees
		double centreLongitude = (m_maxLongitude + m_minLongitude) / 2;
		System.out.println(centreLatitude + " x " + centreLongitude);


		// zoom is decided by the max span of longitude and an adjusted latitude span
		// the relationship between the latitude span and the longitude span is /cos
		double latitudeSpan = m_maxLatitude - m_minLatitude;
		latitudeSpan = latitudeSpan / Math.cos(radianOfCentreLatitude);
		double longitudeSpan = m_maxLongitude - m_minLongitude;
		double zoom = getZoom(Math.max(longitudeSpan, latitudeSpan)) + 1;


		/**
		 * create the x,y co-ordinates for the centre as they would appear on a map of the earth
		 */
		double power = Math.pow(2.00, zoom);
		double realWidth = 256.00 * power;
		// ** result 1 - pixel size of a degree **
		double oneDegree = realWidth / 360.00;
		double radianLength = realWidth / (2.00 * Math.PI);
		// ** result 2 ** the centre on our virtual map
		double centreY = radianLength * atanhCentre;
		
		/**
		 * now we go though the providers creating the x,y's and adjusting them to the virtual frame of our
		 * map using our centreX,Y values
		 */  
		for (int i = 0; i<markerList.size(); i++) 
		{
			Location markerDetails = markerList.get(i); 
			double currentLatitude = markerDetails.getLatitude();
			double currentLongitude = markerDetails.getLongitude();
			double pixelLongitude = (currentLongitude - centreLongitude) * oneDegree;
			double pixelLatitudeRadians = currentLatitude * Math.PI / 180.00;
			double localAtanh = atanh(Math.sin(pixelLatitudeRadians));
			double realPixelLatitude = radianLength * localAtanh;
			double pixelLatitude = centreY - realPixelLatitude; // convert from our virtual map to the displayed portion
			pixelLongitude = pixelLongitude + (MAP_SIZE/2);
			pixelLatitude = pixelLatitude + (MAP_SIZE/2);
			int roleOverX = (int)(Math.floor(pixelLongitude)) ;
			int roleOverY = (int)(Math.floor(pixelLatitude));
			// now create whatever div you want with the given roleOverX and roleOverY so they overlay the map
			// add them to a List or just concatenate a string in this loop and then return .
			//e.g -
			String locationPixelCoordinates = "name=" + markerDetails.toString() + "\n left:" + roleOverX + "px;\n top:" + roleOverY + "px;\n";
			returnString.append( locationPixelCoordinates );
			returnString.append( "\n");
		}	

		return returnString.toString(); //if using the example above
	}
	
	public String getMapCenterInLongLat(List<Location> markerList) {
		/**
		 * find our centre - we can reuse some of these variable later
		 */	
		double m_maxLatitude = getLocationsInfo("max", "Latitude", markerList);
		double m_minLatitude = getLocationsInfo("min", "Latitude", markerList);
		double m_maxLongitude = getLocationsInfo("max", "Longitude", markerList);
		double m_minLongitude = getLocationsInfo("min", "Longitude", markerList);
		double atanhsinO = atanh(Math.sin(m_maxLatitude * Math.PI / 180.00));
		double atanhsinD = atanh(Math.sin(m_minLatitude * Math.PI / 180.00));
		double atanhCentre = (atanhsinD + atanhsinO) / 2;
		double radianOfCentreLatitude = Math.atan(Math.sinh(atanhCentre));
		double centreLatitude = radianOfCentreLatitude * 180.00 / Math.PI; //turn it to degrees
		double centreLongitude = (m_maxLongitude + m_minLongitude) / 2;
		return (centreLatitude + " x " + centreLongitude);
	}
	
	private double getLocationsInfo(String maxMin, String latLong, List<Location> locations) {
		String wantedInfo = maxMin + latLong;
		switch(wantedInfo) {
			case "maxLatitude": System.out.println("maxLat" + getLocationsMaxLatitude(locations));
			return getLocationsMaxLatitude(locations);
			case "minLatitude": System.out.println("minLat" + getLocationsMinLatitude(locations));
				return getLocationsMinLatitude(locations); 
			case "maxLongitude": System.out.println("maxLong" + getLocationsMaxLongitude(locations));
				return getLocationsMaxLongitude(locations); 
			case "minLongitude": System.out.println("minLong" + getLocationsMinLongitude(locations));
				return getLocationsMinLongitude(locations); 
			default: return 1.1;
		}
	}
	
	private double getLocationsMaxLatitude(List<Location> locations) {
		double maxLatitude = locations.get(0).getLatitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLatitude() > maxLatitude) {
				maxLatitude = location.getLatitude();
			}
		}
		return maxLatitude;
	}
	
	private double getLocationsMinLatitude(List<Location> locations) {
		double minLatitude = locations.get(0).getLatitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLatitude() < minLatitude) {
				minLatitude = location.getLatitude();
			}
		}
		return minLatitude;
	}
	
	private double getLocationsMaxLongitude(List<Location> locations) {
		double maxLongitude = locations.get(0).getLatitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLongitude() > maxLongitude) {
				maxLongitude = location.getLongitude();
			}
		}
		return maxLongitude;
	}
	
	private double getLocationsMinLongitude(List<Location> locations) {
		double minLongitude = locations.get(0).getLatitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLongitude() < minLongitude) {
				minLongitude = location.getLongitude();
			}
		}
		return minLongitude;
	}
	
	
}
