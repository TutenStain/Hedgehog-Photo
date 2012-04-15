package se.cth.hedgehogphoto;

public class Location {
	private String location;
	private double longitude, latitude;
	
	public Location(String place) {
		setLocation(place);
	}
	
	public static void main(String [] args) {
		String hej = "hejsasn";
		int index = hej.indexOf('s');
		int index2 = hej.indexOf('s', index);
		System.out.println(hej.substring(0,index));
		System.out.println(hej.substring(index,hej.length()));
		String e = " 42";
		e = e.trim();
		System.out.println(Integer.valueOf(e) + " " + index + index2);
		
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
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLatitude(String latitude) {
		setLatitude( extractCoordinate(latitude) );
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setLongitude(String longitude) {
		setLongitude( extractCoordinate(longitude) );
	}
	
	private double extractCoordinate(String latitude) {
		//will be on form 62, 24, 5170/100 (51,7)
		int degreeEndIndex = latitude.indexOf(',');
		int minutesEndIndex = latitude.indexOf(',', degreeEndIndex + 2); //+2 to avoid having the same ',' and the space
		
		double degree = Double.valueOf( latitude.substring(0,degreeEndIndex) );
		double minutes = Double.valueOf( latitude.substring(degreeEndIndex + 2, minutesEndIndex) );
		String secondsString = latitude.substring(minutesEndIndex + 2, latitude.length());
		double seconds = getSeconds(secondsString);
		
		minutes += seconds / 60;
		degree += minutes / 60;
		return degree;
	}
	
	private double getSeconds(String secondsString) {
		//gets a string of the form '5170/10 (51,7)' - {propably}
		double seconds;
		secondsString = secondsString.trim(); 
		int slashIndex = secondsString.indexOf('/');
		int parenthesisIndex = secondsString.indexOf('(');
		int denominatorEndIndex = parenthesisIndex != -1 ? parenthesisIndex : secondsString.length();
		if (slashIndex != -1) {
			String numeratorString = secondsString.substring(0, slashIndex);
			String denominatorString = secondsString.substring(slashIndex + 1, denominatorEndIndex).trim();
			double numerator = Integer.valueOf(numeratorString);
			double denominator = Integer.valueOf(denominatorString);
			seconds = numerator / denominator;
		} else {
			String temp = secondsString.substring(0, denominatorEndIndex).trim(); 
			seconds = Double.valueOf(temp);
		}
		
		return seconds;
	}
	
	public boolean equals(Location otherLocation) {
		String secondLocation = otherLocation.toString();
		return location.equalsIgnoreCase(secondLocation);
	}
	
	public String toString() {
		return location + "\nlong: " + longitude + "\tlatitude: " + latitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
