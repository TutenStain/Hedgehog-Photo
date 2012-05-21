package se.cth.hedgehogphoto.geocoding.model;

import java.awt.Point;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;

/**
 * Creates a valid URL for making a request to 
 * the nominatim-server.
 * @author Florian Minges
 */
public class URLCreator {
	private static URLCreator urlCreator;
	private StringBuilder builder;
	private static final String NAMEFINDER_URL = "http://nominatim.openstreetmap.org/";
	private static final String XML_FORMAT = "format=xml";
	private static final String ADDRESS_DETAILS = "addressdetails=0";
	private static final String EMAIL = "email=hedgehogphoto.chalmers@gmail.com";
	private static final char AMPERSAND = '&';
	
	private static final String GEOCODING_REQUEST = "search?";
	private static final String GEOCODING_QUERY = "q=";
	
	private static final String REVERSE_GEOCODING_REQUEST = "reverse?";
	private static final String LONGITUDE_QUERY = "lon=";
	private static final String LATITUDE_QUERY = "lat=";
	private static final String ZOOM = "zoom=12";
	
	private URLCreator() {
	}
	
	public static synchronized URLCreator getInstance() {
		if (urlCreator == null)
			urlCreator = new URLCreator();
		return urlCreator;
	}  
	
	public URL queryGeocodingURL(String query) {
		initializeStringBuilder(RequestType.GEOCODING_REQUEST);
		if (query == null) {
			query = "";
		}
		query = encodeString(query);
		this.builder.append(GEOCODING_QUERY);
		this.builder.append(query);
		try {
			return new URL(this.builder.toString());
		} catch (MalformedURLException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to create URL \"" + this.builder.toString() + "\".", e);
			return null;
		}
	}
	
	public URL queryReverseGeocodingURL(Point.Double coords) {
		initializeStringBuilder(RequestType.REVERSE_GEOCODING_REQUEST);
		if (coords == null) {
			return null;
		}
		
		this.builder.append(URLCreator.LONGITUDE_QUERY);
		this.builder.append(coords.x);
		this.builder.append(URLCreator.AMPERSAND);
		this.builder.append(URLCreator.LATITUDE_QUERY);
		this.builder.append(coords.y);
		
		try {
			return new URL(this.builder.toString());
		} catch (MalformedURLException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to create URL \"" + this.builder.toString() + "\".", e);
			return null;
		}
	}
	
	private String encodeString(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			Log.getLogger().log(Level.WARNING, "Could not encode \"" + string + "\" with UTF-8.");
			return string; 
		}
	}

	
	/**
	 * Initializes the string builder so that it contains 
	 * all the necessary info that comes apart from the query
	 * itself. 
	 */
	private void initializeStringBuilder(RequestType requestType) {
		this.builder = new StringBuilder(URLCreator.NAMEFINDER_URL);
		
		switch (requestType) {
		case GEOCODING_REQUEST: 
			this.builder.append(URLCreator.GEOCODING_REQUEST);
			prepareBuilder(); break;
		case REVERSE_GEOCODING_REQUEST: 
			this.builder.append(URLCreator.REVERSE_GEOCODING_REQUEST);
			prepareBuilderForReverseGeocoding(); break;
		default: break;
		}
		
		//DO NOT ENCODE
	}
	
	private void prepareBuilderForReverseGeocoding() {
		prepareBuilder();
		this.builder.append(URLCreator.ZOOM);
		this.builder.append(URLCreator.AMPERSAND);
	}
	
	private void prepareBuilder() {
		this.builder.append(URLCreator.XML_FORMAT);
		this.builder.append(URLCreator.AMPERSAND);
		this.builder.append(URLCreator.ADDRESS_DETAILS);
		this.builder.append(URLCreator.AMPERSAND);
		this.builder.append(URLCreator.EMAIL);
		this.builder.append(URLCreator.AMPERSAND);
	}
	
	
	
}
