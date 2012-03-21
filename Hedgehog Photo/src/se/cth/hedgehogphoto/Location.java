package se.cth.hedgehogphoto;

public class Location {
	private String location;
	
	public Location(String place) {
		setLocation(place);
	}
	
	public Location(double longitud, double latitud) {
		setLocation(longitud, latitud);
	}
	
	public void setLocation(String place) {
		location = place;
	}
	
	public void setLocation(double longitud, double latitud) {
		location = ( String.valueOf(longitud) + "," + String.valueOf(latitud) );
	}
	
	public boolean equals(Location otherLocation) {
		String secondLocation = otherLocation.toString();
		return location.equalsIgnoreCase(secondLocation);
	}
	
	public String toString() {
		return location;
	}
}
