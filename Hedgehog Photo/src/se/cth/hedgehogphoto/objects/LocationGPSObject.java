package se.cth.hedgehogphoto.objects;



public class LocationGPSObject {
	private String location;
	private double longitude = 200;
	private double latitude = 100;
	
	public LocationGPSObject(String location) {
		setLocation(location);
	}
	
	public LocationGPSObject(double longitude, double latitude) {
		setLocation(longitude, latitude);
	}
	
	public void setLocation(String location) {
		this.location = location;
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
	
	/**
	 * Given a string that contains coordinate-information,
	 * this method returns it's value as a double. The string
	 * may be formatted like this (separations with a comma):
	 * degree
	 * degree, minutes
	 * degree, minutes, seconds
	 * @param coordinate the string containing the coordinate-info.
	 * @return the coordinate as a double.
	 */
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
	
	/**
	 * Given an input string, this method strips out everything
	 * that is not within the parenthesis. If no parenthesis, or
	 * only one is found the trimmed string gets returned. 
	 * ie, the input string 'hej (32.44) gud' would return '32.44'. 
	 * the input string 'crazy (56' would return 'crazy (56'. 
	 * @param data the input string.
	 * @return a String without the, in this program, unnecessary
	 * string characters.
	 */
	private String removeIrrelevantData(String data) { //TODO: Change method-name?
		data = data.trim();
		int startParenthesisIndex = data.indexOf('(');
		int endParenthesisIndex = data.indexOf(')');
		if (indexPositionsAreOk(startParenthesisIndex, endParenthesisIndex)) {
			data = data.substring(startParenthesisIndex + 1, endParenthesisIndex);
		}
		return data;
	}
	
	/**
	 * Given a string, this method returns the same string, but commas
	 * within two parenthesis-clauses are converted into points/dots.
	 * That way Java can read the inners of the clauses as doubles
	 * (if it really are numbers there, which in this class case will be).
	 * @param string the string to modify
	 * @return the same string as given, but with commas within two
	 * parenthesis-clauses replaced by dots/points.
	 */
	private String replaceCommasWithinParenthesis(String string) {
		StringBuilder sb = new StringBuilder();
		int tempIndex = 0;
		int startParenthesisIndex = string.indexOf('(', tempIndex);
		int endParenthesisIndex = string.indexOf(')', tempIndex);
		while (indexPositionsAreOk(startParenthesisIndex, endParenthesisIndex)) {
			String unalteredText = string.substring(tempIndex, startParenthesisIndex);
			String parenthesisText = string.substring(startParenthesisIndex, endParenthesisIndex);
			parenthesisText = parenthesisText.replace(',', '.');
			sb.append(unalteredText);
			sb.append(parenthesisText);
			
			/* Prepare for next iteration */
			tempIndex = endParenthesisIndex;
			startParenthesisIndex = string.indexOf('(', tempIndex);
			endParenthesisIndex = string.indexOf(')', tempIndex);
		}
		int lastIndex = string.length();
		String endOfString = string.substring(tempIndex, lastIndex);
		sb.append(endOfString);
		return sb.toString();
	}
	
	/**
	 * Given two integers, this method returns true if:
	 * no input equals -1 and indexOne is smaller or equals indexTwo.
	 * Usage: say you want to see what is in between two parenthesis
	 * signs in a string. You get the index of both these signs, and 
	 * by using this method, you can see if they are placed properly.
	 * ie not 'he (k', 	')he(' or 	'hej)'.
	 * @param indexOne the index of the sign which is supposed to come first.
	 * @param indexTwo the index of the sign which is supposed to come second.
	 * @return true if their positions are ok.
	 */
	private boolean indexPositionsAreOk(int indexOne, int indexTwo) {
		return (indexOne != -1) && (indexTwo != -1) && (indexOne <= indexTwo);
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
	public String getLocation() {
		return location;
	}
	/*public void findLocationPlace(){
		Point.Double p= new Point.Double();
		p.setLocation(this.getLatitude(), this.getLongitude());
		URL url =URLCreator.getInstance().queryReverseGeocodingURL(p);
		LocationObjectOther newlocationObject = XMLParser.getInstance().processReverseGeocodingSearch(url);
		this.setLocation( newlocationObject.getLocation());
	}*/	
}
