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
public class CopyOfPixelFinder {
	//TODO MAP_SIZE: there is currently a constant in the pixelfinder,
	//should be placed somewhere more appropriate.
	private final int MAP_SIZE = 256; //TODO REMOVE: constant MAP_SIZE
	private final int MAP_WIDTH = 256;
	private final int MAP_HEIGHT = 256;
	private final int OFFSET = 268435456;
	private final double RADIUS = 85445659.4471; //OFFSET / Math.PI;
	private final int MAX_ZOOM = 21;

	private double atanh(double rad) {
		return Math.log(((1 + rad) / (1 - rad))) / 2;
	}

	private int getZoom(double span) {
		double zoom = (180.00 / span) * (MAP_SIZE / 256.00);
		zoom = Math.log(zoom)/Math.log(2.0); //logaritm of 'zoom' base 2
		System.out.println("Zoom: " + Math.floor(zoom) + "\n" + (Math.abs(span) > 0.1 ? (int)Math.floor(zoom) : 21));
		return Math.abs(span) > 0.1 ? (int)Math.floor(zoom): 21;

	}
	
	//TODO REFACTOR: two methods (averageLatitude, averageLongitude)
	private double averageLatitude(List<Location> locations) {
		double totalLatitude = 0.0;
		for(int i = 0; i<locations.size(); i++) {
			totalLatitude += locations.get(i).getLatitude();
		}
		double averageLatitude = totalLatitude / locations.size(); //doesn't handle empty list
		return averageLatitude;
	}
	
	private double averageLongitude(List<Location> locations) {
		double totalLongitude = 0.0;
		for(int i = 0; i<locations.size(); i++) {
			totalLongitude += locations.get(i).getLongitude();
		}
		double averageLongitude = totalLongitude / locations.size(); //doesn't handle empty list
		return averageLongitude;
	}
	
	public static void main(String [] args) {
		//test longitudeToX, latitudeToY and vice versa
		CopyOfPixelFinder pixel = new CopyOfPixelFinder();
		double longitude = 15.88;
		double latitude = 33.99;
		int x = 0;
		int y = 0;
		System.out.println("Startvalues: 15.88 longitude and 33.99 latitude. \n");
		for(int i = 0; i<20; i++) {
			x = pixel.longitudeToX(longitude);
			y = pixel.latitudeToY(latitude);
			longitude = pixel.xToLongitude(x);
			latitude = pixel.yToLatitude(y);
			
			System.out.println("x: " + x + "\t\ty: " + y);
			System.out.println("long: " + longitude + "\t\tlat: " + latitude);
			
		}
	}
	
	private int longitudeToX(double longitude) {
		double x = Math.floor(OFFSET + OFFSET * longitude / 180);
		return (int)x;
	}
	
	private int latitudeToY(double latitude) {
		double radian = Math.sin(latitude * Math.PI / 180);
		double x = Math.floor( OFFSET - RADIUS*atanh(radian) );
		return (int)x;
	}
	
	private double xToLongitude(int x) {
		double longitude = ((double)(x - OFFSET) / (double)OFFSET) * 180;
		return longitude;
	}
	
	private double yToLatitude(int y) {
		double temp = Math.exp( (y - OFFSET) / RADIUS );
		double latitude = ((Math.PI / 2) - 2 * Math.atan(temp)) * (180 / Math.PI);
		return latitude;
	}
	
	private int bitwiseRightShift(int leftNumber, int rightNumber) {
		for(int i = 0; i < rightNumber; i++) {
			leftNumber = leftNumber / 2;
		}
		return leftNumber;
	}

	public String getPixelCoordinates(List<Location> markerList) {
		//TODO Refactor this class, split up in several smaller functions; READABILITY!
		
		StringBuilder returnString = new StringBuilder("");
		/** find max and min latitude/longitude */
		double maxLatitude = getLocationsInfo("max", "Latitude", markerList);
		double minLatitude = getLocationsInfo("min", "Latitude", markerList);
		double maxLongitude = getLocationsInfo("max", "Longitude", markerList);
		double minLongitude = getLocationsInfo("min", "Longitude", markerList);
		
		/** Need these four lines to calculate the proper span further down in code*/
		double atanhsinO = atanh(Math.sin(maxLatitude * Math.PI / 180.00)); 
		double atanhsinD = atanh(Math.sin(minLatitude * Math.PI / 180.00));
		double atanhCenter = (atanhsinD + atanhsinO) / 2;
		double radianOfCenterLatitude = Math.atan(Math.sinh(atanhCenter));

		/** Calculate the average latitude and longitude of the markers */
		double averageLatitude = averageLatitude(markerList);
		double averageLongitude = averageLongitude(markerList);
//		double centerLatitude = mapCenterLatitude(markerList);
//		double centerLongitude = mapCenterLongitude(markerList);
		
		/** Calculate center as pixel coordinates in a world map */
		int centerX_WorldPixel = longitudeToX(averageLongitude); 
		int centerY_WorldPixel = latitudeToY(averageLatitude);
		System.out.println("centerX_WorldPixel: " + centerX_WorldPixel);
		System.out.println("centerY_WorldPixel: " + centerY_WorldPixel);
		
		/** Calculate center as pixel coordinates in this image */
		int centerX_ImagePixel = Math.round(MAP_WIDTH / 2);
		int centerY_ImagePixel = Math.round(MAP_HEIGHT / 2);
		System.out.println("center: (" + centerX_ImagePixel + "," + centerY_ImagePixel + ")");
		
		/** zoom is decided by the max span of longitude and an adjusted latitude span
		the relationship between the latitude span and the longitude span is /cos */
		double latitudeSpan = maxLatitude - minLatitude;
		latitudeSpan = latitudeSpan / Math.cos(radianOfCenterLatitude);
		double longitudeSpan = maxLongitude - minLongitude;
		int zoom = getZoom(Math.max(longitudeSpan, latitudeSpan));
		
		for(Location location : markerList) {
			int targetX_World = longitudeToX( location.getLongitude() );
			int targetY_World = latitudeToY( location.getLatitude() );
			
			int leftNumber = targetX_World - centerX_WorldPixel;
			int rightNumber = MAX_ZOOM - zoom;
			int deltaX = bitwiseRightShift(leftNumber, rightNumber);
			
			leftNumber = targetY_World - centerY_WorldPixel;
			int deltaY = bitwiseRightShift(leftNumber, rightNumber);
			
//			System.out.println("före: " + deltaX + " , " + deltaY);
//			deltaX = (int)((targetX_World - centerX_World) / Math.pow(2, (MAX_ZOOM - zoom))); this produces the same result as bitwise right shift
//			deltaY = (int)((targetY_World - centerY_World) / Math.pow(2, (MAX_ZOOM - zoom)));
//			System.out.println("efter: " + deltaX + " , " + deltaY);
			
			int markerX_Image = centerX_ImagePixel + deltaX;
			int markerY_Image = centerY_ImagePixel + deltaY;
			
			String locationPixelCoordinates = "name=" + location.toString() + "\n\"from left (x):" + (MAP_WIDTH - markerY_Image) + "px;\n\"from top (y):" + (MAP_HEIGHT - markerX_Image) + "px;\n";
			returnString.append( locationPixelCoordinates );
			returnString.append( "\n");
		}

		return returnString.toString(); 
	}
	
	public String getMapCenterInLongLat(List<Location> markerList) {
		/** Calculate the average latitude and longitude of the markers */
		double averageLatitude = averageLatitude(markerList);
		double averageLongitude = averageLongitude(markerList);
		return (averageLongitude + "," + averageLatitude);
	}
	
	public double mapCenterLongitude(List<Location> markerList) {
		double m_maxLongitude = getLocationsInfo("max", "Longitude", markerList);
		double m_minLongitude = getLocationsInfo("min", "Longitude", markerList);
		double centerLongitude = (m_maxLongitude + m_minLongitude) / 2;
		return centerLongitude;
	}
	
	public double mapCenterLatitude(List<Location> markerList) {
		double m_maxLatitude = getLocationsInfo("max", "Latitude", markerList);
		double m_minLatitude = getLocationsInfo("min", "Latitude", markerList);
		double atanhsinO = atanh(Math.sin(m_maxLatitude * Math.PI / 180.00));
		double atanhsinD = atanh(Math.sin(m_minLatitude * Math.PI / 180.00));
		double atanhCentre = (atanhsinD + atanhsinO) / 2;
		double radianOfCentreLatitude = Math.atan(Math.sinh(atanhCentre));
		double centerLatitude = radianOfCentreLatitude * 180.00 / Math.PI; //turn it to degrees
		centerLatitude = (m_maxLatitude + m_minLatitude) / 2;
		return centerLatitude;
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
		double maxLongitude = locations.get(0).getLongitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLongitude() > maxLongitude) {
				maxLongitude = location.getLongitude();
			}
		}
		return maxLongitude;
	}
	
	private double getLocationsMinLongitude(List<Location> locations) {
		double minLongitude = locations.get(0).getLongitude();
		for(Location location : locations) { //does one loop too much, nvm
			if(location.getLongitude() < minLongitude) {
				minLongitude = location.getLongitude();
			}
		}
		return minLongitude;
	}
	
	
}
