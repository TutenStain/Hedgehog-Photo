package se.cth.hedgehogphoto.map.geocoding.model;

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
	private final String NAMEFINDER_URL = "http://nominatim.openstreetmap.org/";
	private final String XML_FORMAT = "format=xml";
	private final String ADDRESS_DETAILS = "addressdetails=0";
	private final String EMAIL = "email=";
	private final char AMPERSAND = '&';
	
	private final String GEOCODING_REQUEST = "search?";
	private final String GEOCODING_QUERY = "q=";
	
	private final String REVERSE_GEOCODING_REQUEST = "reverse?";
	private final String LONGITUDE_QUERY = "lon=";
	private final String LATITUDE_QUERY = "lat=";
	private final String ZOOM = "zoom=12";
	
	private enum RequestType { GEOCODING_REQUEST, REVERSE_GEOCODING_REQUEST };
	
	
	private URLCreator() {
	}
	
	public static synchronized URLCreator getInstance() {
		if (urlCreator == null)
			urlCreator = new URLCreator();
		return urlCreator;
	}
	
//	public synchronized void invokeLater() {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Thread.sleep(1000); //makes the thread sleep 1 sec before it returns a string
//				} catch (InterruptedException e) {
//					
//				}
//				
//			}
//		});
//	}
      
	
	public URL queryGeocodingURL(String query) {
		initializeStringBuilder(RequestType.GEOCODING_REQUEST);
		if (query == null) {
			query = "";
		}
		query = encodeString(query);
		this.builder.append(GEOCODING_QUERY);
		this.builder.append(query);
		try {
//			invokeLater();
			return new URL(this.builder.toString());
		} catch (MalformedURLException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to create URL \"" + this.builder.toString() + "\".", e);
			return null;
		}
	}
	
	public URL queryReverseGeocodingURL(Point.Double coords) {
		initializeStringBuilder(RequestType.REVERSE_GEOCODING_REQUEST);
		if (coords == null) {
			try {
				return new URL("");
			} catch (MalformedURLException e) {
				//if this happens... well, then goodnight, will throw NullPointerExe below
			}
			
		}
		
		this.builder.append(this.LONGITUDE_QUERY);
		this.builder.append(coords.x);
		this.builder.append(this.AMPERSAND);
		this.builder.append(this.LATITUDE_QUERY);
		this.builder.append(coords.y);
		
		try {
//			invokeLater();
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
			return string; //TODO: log it?
		}
	}

	
	/**
	 * Initializes the string builder so that it contains 
	 * all the necessary info that comes apart from the query
	 * itself. 
	 */
	private void initializeStringBuilder(RequestType requestType) {
		this.builder = new StringBuilder(this.NAMEFINDER_URL);
		this.builder.append(requestType);
		this.builder.append(this.XML_FORMAT);
		this.builder.append(this.AMPERSAND);
		this.builder.append(this.ADDRESS_DETAILS);
		this.builder.append(this.AMPERSAND);
		
		switch (requestType) {
		case GEOCODING_REQUEST: prepareBuilderForGeocoding(); break;
		case REVERSE_GEOCODING_REQUEST: prepareBuilderForReverseGeocoding(); break;
		default: break;
		}
		
		//DO NOT ENCODE
	}
	
	private void prepareBuilderForGeocoding() {
		this.builder.append(this.GEOCODING_QUERY);
	}
	
	private void prepareBuilderForReverseGeocoding() {
		this.builder.append(this.ZOOM);
		this.builder.append(this.AMPERSAND);
	}
	
}
