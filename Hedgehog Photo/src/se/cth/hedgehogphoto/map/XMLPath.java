package se.cth.hedgehogphoto.map;

public class XMLPath extends URLBuilder {
	private final String addressURL = "address=";
	private final String latlongURL = "latlng=";
	
	public XMLPath() {
		super("http://maps.googleapis.com/maps/api/geocode/xml?");
	}

	@Override
	public void validateURL() {
		// TODO validateURL
		if( !this.containsString(sensorURL) ) {
			urlBuilder.append('&');
			urlBuilder.append(sensorURL);
		} 
		if( !this.containsString(addressURL) && !this.containsString(latlongURL) ) {
			//TODO should throw some kind of error; no address (string/latlong) has been set
		} 
	}
	
	public void setAddress(String address) {
		addToURL(addressURL, address);
	}
	
	public void setLatitudeAndLongitude(double latitude, double longitude) {
		String latlong = String.valueOf(latitude) + "," + String.valueOf(longitude);
		addToURL(latlongURL, latlong);
	}

}
