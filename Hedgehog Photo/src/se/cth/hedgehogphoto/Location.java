package se.cth.hedgehogphoto;

public class Location {
	private String location;
	private double longitude, latitude;
	
	public Location(String place) {
		setLocation(place);
	}
	
	public Location(double longitude, double latitude) {
		setLocation(longitude, latitude);
	}
	
	public void setLocation(String place) {
		location = place;
	}
	
	public void setLocation(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		location = ( String.valueOf(longitude) + "," + String.valueOf(latitude) );
	}
	
	public boolean equals(Location otherLocation) {
		String secondLocation = otherLocation.toString();
		return location.equalsIgnoreCase(secondLocation);
	}
	
	public String toString() {
		return location;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
