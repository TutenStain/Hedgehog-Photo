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
	
	private double extractCoordinate(String coordinate) {
		//will be on form Double,Double,Double after preparation
		coordinate = coordinate.trim();
		coordinate = prepareCoordinate(coordinate);
		int degreeEndIndex = coordinate.indexOf(',');
		int minutesEndIndex = coordinate.indexOf(',', degreeEndIndex + 1);
		int lastIndex = coordinate.length();
			
		String degreeString = degreeEndIndex != -1 ? coordinate.substring(0,degreeEndIndex) : coordinate.substring(0, lastIndex);
		String minutesString = minutesEndIndex != -1 ? coordinate.substring(degreeEndIndex + 1, minutesEndIndex) : coordinate.substring(degreeEndIndex + 1, lastIndex);
		String secondsString = minutesEndIndex != -1 ? coordinate.substring(minutesEndIndex + 1, lastIndex) : "0.0";
		double degree = Double.valueOf( degreeString );
		double minutes = Double.valueOf( minutesString );
		double seconds = Double.valueOf( secondsString );
		
		minutes += seconds / 60;
		degree += minutes / 60;
		return degree;
	}
	
	/**
	 * If there is a parenthesis in the gps data, eg  '62, 24, 5170/100 (51,7)'
	 * one want to extract that and replace the other numbers. The gps data above
	 * for example would look like this afterwards: '62,24,51.7'.
	 * (The comma in the parenthesis gets converted to a dot, so the JVM can read
	 * it as a double.)
	 * @return see javadoc comments.
	 */
	private String prepareCoordinate(String coordinate) {
		coordinate = replaceCommasWithinParenthesis(coordinate);
		StringBuilder sb = new StringBuilder();
		int commaIndex = coordinate.indexOf(',');
		int tempIndex = 0;

		while (commaIndex != -1) {
			String data = coordinate.substring(tempIndex, commaIndex + 1);
			data = removeIrrelevantData(data);
			sb.append(data);
			tempIndex = commaIndex + 1;
			commaIndex = coordinate.indexOf(',', tempIndex);
		}

		int lastIndex = coordinate.length();
		String endOfString = coordinate.substring(tempIndex, lastIndex);
		endOfString = removeIrrelevantData(endOfString);
		sb.append(endOfString);

		return sb.toString();
	}
	
	private String removeIrrelevantData(String data) {
		data = data.trim();
		int startParenthesisIndex = data.indexOf('(');
		if (startParenthesisIndex != -1) {
			int endParenthesisIndex = data.indexOf(')'); //IF POSSIBLE: Add handling of the unprobable case that there is no end parenthesis
			data = data.substring(startParenthesisIndex + 1, endParenthesisIndex);
		}
		return data;
	}
	
	private String replaceCommasWithinParenthesis(String coordinate) {
		StringBuilder sb = new StringBuilder();
		int tempIndex = 0;
		int startParenthesisIndex = coordinate.indexOf('(', tempIndex);
		int endParenthesisIndex;
		while (startParenthesisIndex != -1) {
			endParenthesisIndex = coordinate.indexOf(')', tempIndex);
			String unalteredText = coordinate.substring(tempIndex, startParenthesisIndex);
			String parenthesisText = coordinate.substring(startParenthesisIndex, endParenthesisIndex);
			parenthesisText = parenthesisText.replace(',', '.');
			sb.append(unalteredText);
			sb.append(parenthesisText);
			
			/* Prepare for next iteration */
			tempIndex = endParenthesisIndex;
			startParenthesisIndex = coordinate.indexOf('(', tempIndex); 		
		}
		int lastIndex = coordinate.length();
		String endOfString = coordinate.substring(tempIndex, lastIndex);
		sb.append(endOfString);
		return sb.toString();
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
